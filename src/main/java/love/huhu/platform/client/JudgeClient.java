package love.huhu.platform.client;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import love.huhu.platform.authorization.UserHolder;
import love.huhu.platform.domain.AnswerRecord;
import love.huhu.platform.domain.JudgeMachine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description
 * @Author nwl
 * @Create 2023-02-25 下午2:59
 */
@Component
@RequiredArgsConstructor
public class JudgeClient {

    private List<JudgeMachine> judgeMachines;
    @Value("${api.manager-server}")
    String managerServerApi;
    public void judge(AnswerRecord record) {
        //获取判题机
//        if (judgeMachines == null || judgeMachines.isEmpty()) {
            String response = HttpRequest.get(managerServerApi + "/api/judgeMachine")
                    .form("enabled", true).execute().body();
            judgeMachines = JSONUtil.toList(response,JudgeMachine.class);
//        }
        //随机获取判题机
        JudgeMachine judgeMachine = judgeMachines.get(new Double(Math.floor(Math.random() * judgeMachines.size())).intValue());

        //发送判题请求

        String result = HttpRequest.post(judgeMachine.getUrl() + "/api/judge")
                .body(JSONUtil.toJsonStr(record))
                .execute().body();
        //结果封装回record
        AnswerRecord resultRecord = JSONUtil.toBean(result, AnswerRecord.class);
        record.copy(resultRecord);
        //todo 保存record到数据库

        record.setUserId(UserHolder.getUserId());

    }
}
