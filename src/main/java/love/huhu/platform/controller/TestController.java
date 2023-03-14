package love.huhu.platform.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import love.huhu.platform.authorization.AuthorizationRequired;
import love.huhu.platform.authorization.PermissionEnum;
import love.huhu.platform.authorization.UserHolder;
import love.huhu.platform.client.ManagerClient;
import love.huhu.platform.domain.Test;
import love.huhu.platform.domain.TestUser;
import love.huhu.platform.service.TestService;
import love.huhu.platform.service.TestUserService;
import love.huhu.platform.service.dto.TestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * @Description
 * @Author nwl
 * @Create 2023-02-25 下午10:59
 */
@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {
    private final ManagerClient managerClient;
    private final TestService testService;
    private final TestUserService testUserService;
    @AuthorizationRequired(PermissionEnum.STUDENT)
    @GetMapping
    public ResponseEntity<Object> getMyTest(Long testId) {
        JSONObject myTest = null;

        List<Long> myTestIds = testService.getMyTestIds(UserHolder.getUser().getDept().getId());
        if (testId == null) {
            myTest = managerClient.getTestByIds(myTestIds);
        } else {
            if (myTestIds.contains(testId)) {
                myTest = managerClient.getTest(testId);
            } else {
                throw new RuntimeException("你未被安排这场测验");
            }
        }
        return new ResponseEntity<>(myTest,HttpStatus.OK);
    }
    @AuthorizationRequired(PermissionEnum.TEACHER)
    @GetMapping("teacher")
    public ResponseEntity<Object> getMyTestForTeacher(Long testId) {
        Object myTest = managerClient.getMyTest(UserHolder.getUserId(),testId);
//        myTest = null;
        if (myTest == null) {
            return new ResponseEntity<>("{\"content\": []}",HttpStatus.OK);
        }
        return new ResponseEntity<>(myTest,HttpStatus.OK);
    }
    @ApiOperation("保存测验记录")
    @AuthorizationRequired
    @PostMapping("record")
    public ResponseEntity<Object> saveTestRecord(@RequestBody TestUser test) {
        return new ResponseEntity<>(testUserService.save(test),HttpStatus.OK);
    }
    @ApiOperation("查询我的测验记录")
    @AuthorizationRequired
    @GetMapping("record")
    public ResponseEntity<Object> getTestRecord(Long testId) {
        return new ResponseEntity<>(testUserService.lambdaQuery()
                .eq(TestUser::getTestId,testId)
                .eq(TestUser::getUserId,UserHolder.getUserId()).one(),HttpStatus.OK);
    }
    @ApiOperation("查询我所有的测验记录")
    @AuthorizationRequired
    @GetMapping("records")
    @Transactional
    public ResponseEntity<Object> getTestRecords() {
        return new ResponseEntity<>(testUserService.lambdaQuery()
                .eq(TestUser::getUserId,UserHolder.getUserId()).list(),HttpStatus.OK);
    }
    @ApiOperation("查询我的所有测验记录")
    @AuthorizationRequired
    @GetMapping("record/all")
    public ResponseEntity<Object> getAllTestRecord() {
        return new ResponseEntity<>(testUserService.lambdaQuery()
                .eq(TestUser::getUserId,UserHolder.getUserId()).one(),HttpStatus.OK);
    }
    @AuthorizationRequired(PermissionEnum.TEACHER)
    @PostMapping
    public ResponseEntity<Object> saveTest(@RequestBody TestDto test) {
        return new ResponseEntity<>(testService.saveTest(test),HttpStatus.OK);
    }
    @AuthorizationRequired(PermissionEnum.TEACHER)
    @PutMapping
    public ResponseEntity<Object> updateTest(@RequestBody TestDto test) {
        return new ResponseEntity<>(managerClient.updateTest(test),HttpStatus.OK);
    }
    @AuthorizationRequired(PermissionEnum.TEACHER)
    @DeleteMapping
    public ResponseEntity<Object> deleteTest(Long testId) {
        return new ResponseEntity<>(managerClient.deleteTests(new Long[] {testId}),HttpStatus.OK);
    }
}
