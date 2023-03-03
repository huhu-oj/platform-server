package love.huhu.platform.controller;

import cn.hutool.json.JSONObject;
import lombok.RequiredArgsConstructor;
import love.huhu.platform.authorization.AuthorizationRequired;
import love.huhu.platform.authorization.UserHolder;
import love.huhu.platform.client.ManagerClient;
import love.huhu.platform.domain.Test;
import love.huhu.platform.service.TestService;
import love.huhu.platform.service.dto.TestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @AuthorizationRequired
    @GetMapping
    public ResponseEntity<Object> getMyTest(Long testId) {
        Object myTest = managerClient.getMyTest(UserHolder.getUserId(),testId);
//        myTest = null;
        if (myTest == null) {
            List<Long> myTestIds = testService.getMyTestIds(UserHolder.getUser().getDeptId());
            myTest = managerClient.getTestByIds(myTestIds);
        }
        return new ResponseEntity<>(myTest,HttpStatus.OK);
    }

    @AuthorizationRequired
    @PostMapping
    public ResponseEntity<Object> saveTest(@RequestBody TestDto test) {
        return new ResponseEntity<>(testService.saveTest(test),HttpStatus.OK);
    }
    @AuthorizationRequired
    @PutMapping
    public ResponseEntity<Object> updateTest(@RequestBody Test test) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @AuthorizationRequired
    @DeleteMapping
    public ResponseEntity<Object> deleteTest(Long testId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
