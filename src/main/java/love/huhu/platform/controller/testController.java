package love.huhu.platform.controller;

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
public class testController {
    @GetMapping
    public ResponseEntity<Object> getTest(Long testId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> saveTest(@RequestBody Test test,Long[] userIds) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<Object> updateTest(@RequestBody Test test) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<Object> deleteTest(Long testId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
