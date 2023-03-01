package love.huhu.platform.controller;

import lombok.RequiredArgsConstructor;
import love.huhu.platform.authorization.AuthorizationRequired;
import love.huhu.platform.client.ManagerClient;
import love.huhu.platform.domain.User;
import love.huhu.platform.dto.UserLoginDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;


/**
 * @Author nwl20
 * @create 2022/9/24 18:27
 */
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class LoginController {

    private final ManagerClient managerClient;
    @AuthorizationRequired
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody @Validated UserLoginDto dto, HttpServletResponse response) {
        User user = managerClient.userLogin(dto, response);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Validated UserLoginDto dtoForRegister) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/code")
    public ResponseEntity<Object> getCode() {
        return new ResponseEntity<>(managerClient.getGraphicCode(),HttpStatus.OK);
    }
}
