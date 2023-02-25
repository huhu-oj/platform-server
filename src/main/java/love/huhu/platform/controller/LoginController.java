package love.huhu.platform.controller;

import lombok.AllArgsConstructor;

import lombok.RequiredArgsConstructor;
import love.huhu.platform.domain.User;
import love.huhu.platform.dto.UserLoginDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Author nwl20
 * @create 2022/9/24 18:27
 */
@RestController
@RequiredArgsConstructor
public class LoginController {

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody @Validated UserLoginDto dto) {
       return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/register")
    @Transactional
    public ResponseEntity<User> register(@RequestBody @Validated UserLoginDto dtoForRegister) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
