package love.huhu.platform;

import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWTUtil;
import love.huhu.platform.client.ManagerClient;
import love.huhu.platform.domain.User;
import love.huhu.platform.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlatformServerApplicationTests {


    @Autowired
    public ManagerClient managerClient;
    @Autowired
    public UserService userService;
    @Test
    public void loginLocal() {
        managerClient.systemLogin();
    }

    @Test
    public void userInfo() {
        Object username = JWTUtil.parseToken("eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiIwMjhhZTUzODM5OTE0NWFiYTM1NDMxZWQ1ZDkyMDFlZCIsInVzZXIiOiJzeXN0ZW0iLCJzdWIiOiJzeXN0ZW0ifQ.rwP9m1rz12MmHvFmbbF4n35FDWyGx7rCR3lAw2lh622RKFbYEgfGjFHWnI8esSWIwaEHk1oT2V15cSOSOnEqsw")
                .getPayload("user");
        System.out.println(username);
    }
    @Test
    public void getUserByName() {
        User user = userService.lambdaQuery().eq(User::getUsername, "admin").one();
        System.out.println(user);
    }
    @Test void getAnswerRecord() {
        managerClient.systemLogin();
        JSONObject answerRecords = managerClient.getAnswerRecords(1L, 1L);
        System.out.println(answerRecords);
    }
}
