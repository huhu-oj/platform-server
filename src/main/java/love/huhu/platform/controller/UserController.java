package love.huhu.platform.controller;

import lombok.RequiredArgsConstructor;
import love.huhu.platform.authorization.AuthorizationRequired;
import love.huhu.platform.authorization.UserHolder;
import love.huhu.platform.client.ManagerClient;
import love.huhu.platform.domain.User;
import love.huhu.platform.service.AnswerRecordService;
import love.huhu.platform.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Description
 * @Author nwl
 * @Create 2023-02-25 上午9:47
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final ManagerClient managerClient;
    private final UserService userService;
    private final AnswerRecordService answerRecordService;
    @GetMapping
    @AuthorizationRequired
    public ResponseEntity<Object> getUserSelfInfo() {
        return new ResponseEntity<>(managerClient.getUserInfo(UserHolder.getToken()), HttpStatus.OK);
    }
    @GetMapping("name")
    @AuthorizationRequired
    public ResponseEntity<Object> getUserByUsername(String username) {
        List<User> users = userService.lambdaQuery().like(User::getNickName, username).list();
        return new ResponseEntity<>(users,HttpStatus.OK);
    }
    @GetMapping("ids")
    @AuthorizationRequired
    public ResponseEntity<Object> getUserByIds(Long [] userIds) {
        List<User> users = userService.lambdaQuery().in(User::getId, Arrays.asList(userIds)).list();
        return new ResponseEntity<>(users,HttpStatus.OK);
    }
    @GetMapping("language")
    @AuthorizationRequired
    public ResponseEntity<Object> getUserOfferUseLanguage() {
       return new ResponseEntity<>( answerRecordService.getUserOfferUseLanguage(UserHolder.getUserId()),HttpStatus.OK);
    }
}
