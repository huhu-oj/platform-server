package love.huhu.platform.controller;

import cn.hutool.json.JSONObject;
import lombok.RequiredArgsConstructor;
import love.huhu.platform.authorization.AuthorizationRequired;
import love.huhu.platform.authorization.UserHolder;
import love.huhu.platform.client.ManagerClient;
import love.huhu.platform.domain.User;
import love.huhu.platform.dto.UserLoginDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;


/**
 * @Author nwl20
 * @create 2022/9/24 18:27
 */
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    @Value("${api.manager-server}")
    private String serverAddr;
    private final ManagerClient managerClient;
    @PostMapping("login")
    public ResponseEntity<Object> login(@RequestBody @Validated UserLoginDto dto) {
        JSONObject response = managerClient.userLogin(dto);
        if (response.get("status") != null && !Objects.equals(response.getInt("status"), 200)) {
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
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
    @GetMapping("addr")
    public ResponseEntity<Object> getManagerAddr() {
        return new ResponseEntity<>(serverAddr,HttpStatus.OK);
    }
}
