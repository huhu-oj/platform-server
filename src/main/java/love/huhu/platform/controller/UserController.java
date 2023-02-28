package love.huhu.platform.controller;

import lombok.RequiredArgsConstructor;
import love.huhu.platform.authorization.AuthorizationRequired;
import love.huhu.platform.authorization.UserHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author nwl
 * @Create 2023-02-25 上午9:47
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {


    @GetMapping
    @AuthorizationRequired
    public ResponseEntity<Object> getUserSelfInfo() {
        return new ResponseEntity<>(UserHolder.getUser(), HttpStatus.OK);
    }
}
