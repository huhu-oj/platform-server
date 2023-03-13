package love.huhu.platform.client;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import love.huhu.platform.authorization.UserHolder;
import love.huhu.platform.domain.AnswerRecord;
import love.huhu.platform.domain.JudgeMachine;
import love.huhu.platform.service.AnswerRecordService;
import love.huhu.platform.service.dto.JudgeMachineDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.json.Json;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.data.redis.connection.ReactiveStreamCommands.AddStreamRecord.body;

/**
 * @Description
 * @Author nwl
 * @Create 2023-02-25 下午2:59
 */
@Component
@RequiredArgsConstructor
public class JudgeClient {
    private List<JudgeMachineDto> judgeMachines;
    @Value("${api.manager-server}")
    String managerServerApi;
    private final ManagerClient managerClient;
    private final AnswerRecordService answerRecordService;
    public void judge(AnswerRecord record) {
        JudgeMachineDto judgeMachine = getRandomHost(record.getLanguageId());
        //发送判题请求

        String result = HttpRequest.post(judgeMachine.getUrl() + "/api/v1/judge")
                .body(JSONUtil.toJsonStr(record))
                .execute().body();
        //结果封装回record
        AnswerRecord resultRecord = dealResult(result);
        record.copy(resultRecord);
        record.setUserId(UserHolder.getUserId());
        //保存record到数据库
        if (record.getExecuteResultId() <= 0) {
            throw new RuntimeException("判题失败");
        }
        answerRecordService.save(record);

    }

    private JudgeMachineDto getRandomHost(Long languageId) {
        //获取判题机
        judgeMachines = managerClient.getEnabledJudgeMachine();
        if (judgeMachines.isEmpty()) {
            throw new RuntimeException("暂无判题资源");
        }
        //随机获取判题机
        List<JudgeMachineDto> supportedJudgeMachines = judgeMachines.stream()
                .filter(judgeMachine -> judgeMachine.getLanguages().stream().anyMatch(languageDto -> Objects.equals(languageDto.getId(), languageId)))
                .collect(Collectors.toList());

        if (supportedJudgeMachines.isEmpty()) {
            throw new RuntimeException("暂无判题资源");
        }
        return supportedJudgeMachines.get(new Double(Math.floor(Math.random() * judgeMachines.size())).intValue());

    }

    public AnswerRecord judgeTest(AnswerRecord record) {
        //获取判题机
        JudgeMachineDto judgeMachine = getRandomHost(record.getLanguageId());
        //发送判题请求
        HttpResponse response = HttpRequest.post(judgeMachine.getUrl() + "/api/v1/test")
                .body(JSONUtil.toJsonStr(record))
                .execute();
        if (response.getStatus() == 500) {
            throw new RuntimeException((String) JSONUtil.parseObj(response.body()).getStr("message"));
        }
        String result = response.body();
        AnswerRecord answerRecord = dealResult(result);
        return answerRecord;
    }

    private AnswerRecord dealResult(String result) {
        JSONObject temp = JSONUtil.parseObj(result);
        String error = temp.get("Error", String.class);
        String log = temp.get("Log",String.class);
        Integer passNum = temp.get("PassNum",Integer.class);
        Integer notPassNum = temp.get("NotPassNum",Integer.class);
        Long executeResultId = temp.get("ExecuteResultId",Long.class);
        AnswerRecord record = new AnswerRecord();
        record.setError(error);
        record.setLog(log);
        record.setPassNum(passNum);
        record.setNotPassNum(notPassNum);
        record.setExecuteResultId(executeResultId);
        return record;
    }

}
