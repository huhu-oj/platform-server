package love.huhu.platform;

import love.huhu.platform.client.ManagerClient;
import love.huhu.platform.domain.User;
import love.huhu.platform.dto.UserLoginDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlatformServerApplicationTests {


    @Autowired
    public ManagerClient managerClient;
    @Test
    public void loginLocal() {
        managerClient.systemLogin();
    }
}
