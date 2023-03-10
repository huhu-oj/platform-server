package love.huhu.platform.controller;

import lombok.RequiredArgsConstructor;
import love.huhu.platform.authorization.AuthorizationRequired;
import love.huhu.platform.authorization.PermissionEnum;
import love.huhu.platform.client.JudgeClient;
import love.huhu.platform.domain.AnswerRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Description
 * @Author nwl
 * @Create 2023-02-25 下午3:01
 */
@RestController
@RequestMapping("/api/judge")
@RequiredArgsConstructor
public class JudgeController {

    private final JudgeClient judgeClient;

    @AuthorizationRequired(PermissionEnum.STUDENT)
    @PostMapping
    public ResponseEntity<Object> judge(@RequestBody @Valid AnswerRecord record) {
        judgeClient.judge(record);
        return new ResponseEntity<>(record,HttpStatus.OK);
    }
    @AuthorizationRequired(PermissionEnum.STUDENT)
    @PostMapping("test")
    public ResponseEntity<Object> test(@RequestBody AnswerRecord answerRecord) {
        AnswerRecord result = judgeClient.judgeTest(answerRecord);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
