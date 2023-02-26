package love.huhu.platform.client;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import love.huhu.platform.config.porperties.AuthorizationProperties;
import love.huhu.platform.domain.User;
import love.huhu.platform.dto.UserLoginDto;
import love.huhu.platform.service.UserService;
import love.huhu.platform.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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

    public static final String cacheKey = "USER-LOGIN-DATA";
    private final AuthorizationProperties properties;
    private final RedisUtils redisUtils;
    private final UserService userService;
    private String token;

    /**
     * 系统登录接口
     */
    public void systemLogin() {
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setUsername(username);
        userLoginDto.setPassword(password);
        JSONObject response = login(userLoginDto);
        token = response.getStr("token");
    }
    private JSONObject login(UserLoginDto userLoginDto) {
        //RSA加密
        RSA rsa = new RSA(null, publicKey);
        userLoginDto.setPassword(rsa.encryptBase64(userLoginDto.getPassword(), KeyType.PublicKey));
        //userLoginDto->json
        String request = JSONUtil.toJsonStr(userLoginDto);
        //发送请求
        String response = managerPost("/auth/login")
                .body(request)
                .execute().body();
        //处理响应
        JSONObject responseObj = JSONUtil.parseObj(response);
        return responseObj;
    }
     /**
     * 系统登录接口
     */
    public User userLogin(UserLoginDto userLoginDto, HttpServletResponse response) {
        JSONObject responseObj = login(userLoginDto);

        response.addCookie(new Cookie("auth", URLUtil.encode(responseObj.getStr("token"))));
        return JSONUtil.toBean(responseObj.getJSONObject("user").getJSONObject("user"), User.class);
    }

    public User getUserByName(String username) {
        User user = userService.lambdaQuery().eq(User::getUsername, username).one();
        System.out.println(user);
        return user;
    }

    public String getAnswerRecords(Long problemId, Long studentId) {
        String response = managerGet("/api/answerRecord")
                .form("problemId", problemId)
                .form("userId", studentId)
                .header(properties.getHeader(),token)
                .execute().body();

        return response;
    }
    private HttpRequest managerGet(String url) {
        return HttpRequest.get(managerServerApi+url);
    }
    private HttpRequest managerPost(String url) {
        return HttpRequest.post(managerServerApi+url);
    }
    private HttpRequest managerPut(String url) {
        return HttpRequest.put(managerServerApi+url);
    }
    private HttpRequest managerDel(String url) {
        return HttpRequest.delete(managerServerApi+url);
    }
}
