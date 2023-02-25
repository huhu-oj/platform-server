package love.huhu.platform.client;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import love.huhu.platform.domain.User;
import love.huhu.platform.dto.UserLoginDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description
 * @Author nwl
 * @Create 2023-02-25 上午9:49
 */
@Component
@RequiredArgsConstructor
public class ManagerClient {
    @Value("${api.manager-server}")
    String managerServerApi;
    @Value("${verify.public-key}")
    String publicKey;
    @Value("${system.username}")
    String username;
    @Value("${system.password}")
    String password;
    public User getUserById(Long userId) {

        return null;
    }
    private String token;
    public void systemLogin() {
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setUsername(username);
        userLoginDto.setPassword(password);
        //RSA加密
        RSA rsa = new RSA(null, publicKey);
        userLoginDto.setPassword(rsa.encryptBase64(userLoginDto.getPassword(), KeyType.PublicKey));
        //userLoginDto->json
        String request = JSONUtil.toJsonStr(userLoginDto);
        //发送请求
        String response = HttpRequest.post(managerServerApi + "/auth/login/local")
                .body(request)
                .execute().body();
        //处理响应
        JSONObject responseObj = JSONUtil.parseObj(response);
        token = responseObj.getStr("token");
    }
}
