package love.huhu.platform.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import love.huhu.platform.authorization.AuthorizationRequired;
import love.huhu.platform.authorization.PermissionEnum;
import love.huhu.platform.authorization.UserHolder;
import love.huhu.platform.client.ManagerClient;
import love.huhu.platform.domain.AnswerRecord;
import love.huhu.platform.service.AnswerRecordService;
import love.huhu.platform.service.dto.AnswerRecordDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private final AnswerRecordService answerRecordService;
    @GetMapping
    @AuthorizationRequired(PermissionEnum.STUDENT)
    public ResponseEntity<Object> getAnswerRecords(Long testId, Long problemId,Long answerRecordId) {

        return new ResponseEntity<>(managerClient.getAnswerRecords(testId, problemId, UserHolder.getUserId(), answerRecordId),HttpStatus.OK);
    }
    @GetMapping("all")
    @AuthorizationRequired(PermissionEnum.STUDENT)
    public ResponseEntity<Object> getAllAnswerRecords(Long testId, Long problemId, Long answerRecordId, Long[] labelIds) {
        JSONArray allAnswerRecords = managerClient.getAllAnswerRecords(testId, problemId, UserHolder.getUserId(), answerRecordId);
        if (labelIds == null || labelIds.length == 0) {
            return new ResponseEntity<>(allAnswerRecords,HttpStatus.OK);
        }
        List<AnswerRecordDto> answerRecords = JSONUtil.toList(allAnswerRecords, AnswerRecordDto.class);

        List<AnswerRecordDto> result = answerRecords.stream()
                .filter(answerRecordDto -> answerRecordDto.getProblem().getLabels().stream()
                        .anyMatch(labelDto -> Arrays.asList(labelIds).contains(labelDto.getId()))).collect(Collectors.toList());
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @AuthorizationRequired({PermissionEnum.TEACHER})
    @GetMapping("teacher")
    public ResponseEntity<Object> getAnswerRecordsForTeacher(Long problemId,Long studentId) {

        return new ResponseEntity<>(managerClient.getAnswerRecords(null, problemId, studentId, null),HttpStatus.OK);
    }

    @AuthorizationRequired
    @PutMapping
    public ResponseEntity<Object> updateAnswerRecord(@RequestBody AnswerRecord answerRecord) {
        return new ResponseEntity<>(answerRecordService.updateById(answerRecord),HttpStatus.OK);
    }
}
