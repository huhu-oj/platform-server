package love.huhu.platform.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import love.huhu.platform.authorization.UserHolder;
import love.huhu.platform.client.ManagerClient;
import love.huhu.platform.domain.AnswerRecord;
import love.huhu.platform.domain.TestUser;
import love.huhu.platform.service.TestUserService;
import love.huhu.platform.mapper.TestUserMapper;
import love.huhu.platform.service.dto.AnswerRecordDto;
import love.huhu.platform.service.dto.ExaminationPaperProblemDto;
import love.huhu.platform.service.dto.TestDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【oj_test_user(测验用户关联)】的数据库操作Service实现
* @createDate 2023-03-07 17:01:39
*/
@Service
@RequiredArgsConstructor
public class TestUserServiceImpl extends ServiceImpl<TestUserMapper, TestUser>
    implements TestUserService{

    private final ManagerClient managerClient;
    @Override
    public boolean save(TestUser entity) {
        //拿到用户的测验数据
        JSONArray test = managerClient.getTestByIdsWithoutPage(Collections.singletonList(entity.getTestId()));
        TestDto testDto = test.get(0,TestDto.class);
        //拿到对应的试卷题目的分值
        List<ExaminationPaperProblemDto> problems = testDto.getExaminationPaper().getExaminationPaperProblems();
        //拿到用户在这场测验中提交的记录
//        JSONObject answerRecords = managerClient.getAnswerRecords(entity.getTestId(), null, entity.getUserId(), null);
//        List<AnswerRecordDto> toComputeScore = JSONUtil.toList(answerRecords.getJSONArray("content"), AnswerRecordDto.class);
        List<AnswerRecordDto> userAnswerRecords = testDto.getAnswerRecords().stream().filter(answerRecordDto -> UserHolder.getUserId().equals(answerRecordDto.getUserId())).collect(Collectors.toList());
        //计算分数
        BigDecimal sumScore = BigDecimal.ZERO;
        problems.forEach(problem->{
            if (userAnswerRecords.stream()
                    .filter(answerRecordDto -> problem.getProblem().getId().equals(answerRecordDto.getProblem().getId()))
                    .anyMatch(answerRecordDto -> answerRecordDto.getExecuteResult().getId() == 1)) {
                sumScore.add(BigDecimal.valueOf(problem.getScore()));
            }
        });
        entity.setScore(sumScore.doubleValue());
        //序列化测验数据
        entity.setTestJsonStr(JSONUtil.toJsonStr(testDto));
        entity.setUserId(UserHolder.getUserId());
        return super.save(entity);
    }
}




