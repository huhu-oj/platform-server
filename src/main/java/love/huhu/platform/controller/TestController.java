package love.huhu.platform.controller;

import lombok.RequiredArgsConstructor;
import love.huhu.platform.authorization.AuthorizationRequired;
import love.huhu.platform.authorization.UserHolder;
import love.huhu.platform.client.ManagerClient;
import love.huhu.platform.domain.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @AuthorizationRequired
    @GetMapping
    public ResponseEntity<Object> getMyTest(Long testId) {
        return new ResponseEntity<>(managerClient.getMyTest(UserHolder.getUserId(),testId),HttpStatus.OK);
    }

    @AuthorizationRequired
    @PostMapping
    public ResponseEntity<Object> saveTest(@RequestBody Test test,Long[] userIds) {
        return new ResponseEntity<>(HttpStatus.OK);
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
