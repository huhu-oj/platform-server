package love.huhu.platform.controller;

import lombok.RequiredArgsConstructor;
import love.huhu.platform.authorization.AuthorizationRequired;
import love.huhu.platform.authorization.UserHolder;
import love.huhu.platform.client.ManagerClient;
import love.huhu.platform.domain.User;
import love.huhu.platform.dto.UserLoginDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @Author nwl20
 * @create 2022/9/24 18:27
 */
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final ManagerClient managerClient;
    @PostMapping("login")
    public ResponseEntity<Object> login(@RequestBody @Validated UserLoginDto dto, HttpServletResponse response) {
        return new ResponseEntity<>(managerClient.userLogin(dto),HttpStatus.OK);
    }
    @PostMapping("register")
    public ResponseEntity<User> register(@RequestBody @Validated UserLoginDto dtoForRegister) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("code")
    public ResponseEntity<Object> getCode() {
        return new ResponseEntity<>(managerClient.getGraphicCode(),HttpStatus.OK);
    }
    @DeleteMapping("logout")
    @AuthorizationRequired
    public ResponseEntity<Object> logout() {
        return new ResponseEntity<>(managerClient.userLogout(UserHolder.getToken()),HttpStatus.OK);
    }
}
