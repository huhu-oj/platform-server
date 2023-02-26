package love.huhu.platform.controller;

import lombok.RequiredArgsConstructor;
import love.huhu.platform.authorization.UserHolder;
import love.huhu.platform.client.ManagerClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author nwl
 * @Create 2023-02-25 下午10:40
 */
@RestController
@RequestMapping("/answerRecord")
@RequiredArgsConstructor
public class AnswerRecordController {
    private ManagerClient managerClient;
    @GetMapping
    public ResponseEntity<Object> getAnswerRecords(Long problemId) {

        return new ResponseEntity<>(managerClient.getAnswerRecords(problemId, UserHolder.getUserId()),HttpStatus.OK);
    }
    @GetMapping("teacher")
    public ResponseEntity<Object> getAnswerRecordsForTeacher(Long problemId,Long studentId) {

        return new ResponseEntity<>(managerClient.getAnswerRecords(problemId, studentId),HttpStatus.OK);
    }

}
