package love.huhu.platform.controller;

import lombok.RequiredArgsConstructor;
import love.huhu.platform.authorization.AuthorizationRequired;
import love.huhu.platform.client.JudgeClient;
import love.huhu.platform.domain.AnswerRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author nwl
 * @Create 2023-02-25 下午3:01
 */
@RestController
@RequestMapping("/judge")
@RequiredArgsConstructor
public class JudgeController {

    private final JudgeClient judgeClient;

    @AuthorizationRequired
    @PostMapping
    public ResponseEntity<Object> judge(@RequestBody @Validated AnswerRecord record) {
        judgeClient.judge(record);
        return new ResponseEntity<>(record,HttpStatus.OK);
    }
}
