package love.huhu.platform.client;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import love.huhu.platform.authorization.UserHolder;
import love.huhu.platform.config.porperties.AuthorizationProperties;
import love.huhu.platform.domain.Solution;
import love.huhu.platform.domain.Test;
import love.huhu.platform.domain.User;
import love.huhu.platform.dto.UserLoginDto;
import love.huhu.platform.service.UserService;
import love.huhu.platform.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

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
        String response = HttpRequest.post(managerServerApi+"/auth/login")
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

    public JSONObject getAnswerRecords(Long problemId, Long studentId) {
        String response = managerGet("/api/answerRecord")
                .form("problemId", problemId)
                .form("userId", studentId)
                .execute().body();

        return JSONUtil.parseObj(response);
    }
    private HttpRequest managerGet(String url) {
        if (StrUtil.isBlank(token)) {
            systemLogin();
        }
        return HttpRequest.get(managerServerApi+url)
                .header(properties.getHeader(),token);
    }
    private HttpRequest managerPost(String url) {
        if (StrUtil.isBlank(token)) {
            systemLogin();
        }
        return HttpRequest.post(managerServerApi+url)
                .header(properties.getHeader(),token);
    }
    private HttpRequest managerPut(String url) {
        if (StrUtil.isBlank(token)) {
            systemLogin();
        }
        return HttpRequest.put(managerServerApi+url)
                .header(properties.getHeader(),token);
    }
    private HttpRequest managerDel(String url) {
        if (StrUtil.isBlank(token)) {
            systemLogin();
        }
        return HttpRequest.delete(managerServerApi+url)
                .header(properties.getHeader(),token);
    }

    public JSONObject getExaminationPaper(Long paperId) {
        String response = managerGet("/api/examinationPaper")
                .form("id", paperId)
                .execute().body();
        return JSONUtil.parseObj(response);
    }

    public JSONObject getKnowledgeById(Long knowledgeId) {
        String response = managerGet("/api/knowledge")
                .form("id", knowledgeId)
                .execute().body();
        return JSONUtil.parseObj(response);
    }

    public JSONObject getLanguages() {
        String response = managerGet("/api/language")
                .execute().body();
        return JSONUtil.parseObj(response);
    }
    public JSONObject getSolution(Long problemId,Long solutionId) {
        String response = managerGet("/api/solution")
                .form("problemId",problemId)
                .form("solutionId",solutionId)
                .execute().body();
        return JSONUtil.parseObj(response);
    }
    public JSONObject saveSolution(Solution solution) {
        // todo 交给谁做
        String response = managerGet("/api/solution")
                .body(JSONUtil.toJsonStr(solution))
                .execute().body();
        return JSONUtil.parseObj(response);
    }
    public JSONObject deleteSolutions(Long[] solutionIds) {
        String response = managerDel("/api/solution")
                .body(Arrays.toString(solutionIds))
                .execute().body();
        return JSONUtil.parseObj(response);
    }
    public JSONObject getTest(Long testId) {
        String response = managerGet("/api/test")
                .form("id",testId)
                .execute().body();
        return JSONUtil.parseObj(response);
    }
    public JSONObject updateTest(Test test) {
        String response = managerGet("/api/test")
                .body(JSONUtil.toJsonStr(test))
                .execute().body();
        return JSONUtil.parseObj(response);
    }
    public JSONObject deleteTests(Long[] testIds) {
        String response = managerDel("/api/test")
                .body(Arrays.toString(testIds))
                .execute().body();
        return JSONUtil.parseObj(response);
    }
}
