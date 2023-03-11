package love.huhu.platform.controller;

import lombok.RequiredArgsConstructor;
import love.huhu.platform.authorization.AuthorizationRequired;
import love.huhu.platform.authorization.PermissionEnum;
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
@RequestMapping("/api/answerRecord")
@RequiredArgsConstructor
public class AnswerRecordController {
    private final ManagerClient managerClient;
    @GetMapping
    @AuthorizationRequired(PermissionEnum.STUDENT)
    public ResponseEntity<Object> getAnswerRecords(Long testId, Long problemId,Long answerRecordId) {

        return new ResponseEntity<>(managerClient.getAnswerRecords(testId, problemId, UserHolder.getUserId(), answerRecordId),HttpStatus.OK);
    }
    @GetMapping("all")
    @AuthorizationRequired(PermissionEnum.STUDENT)
    public ResponseEntity<Object> getAllAnswerRecords(Long testId, Long problemId,Long answerRecordId) {

        return new ResponseEntity<>(managerClient.getAllAnswerRecords(testId, problemId, UserHolder.getUserId(), answerRecordId),HttpStatus.OK);
    }
    @AuthorizationRequired({PermissionEnum.TEACHER})
    @GetMapping("teacher")
    public ResponseEntity<Object> getAnswerRecordsForTeacher(Long problemId,Long studentId) {

        return new ResponseEntity<>(managerClient.getAnswerRecords(null, problemId, studentId, null),HttpStatus.OK);
    }

}
