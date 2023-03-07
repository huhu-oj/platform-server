package love.huhu.platform.client;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import love.huhu.platform.authorization.UserHolder;
import love.huhu.platform.config.porperties.AuthorizationProperties;
import love.huhu.platform.domain.*;
import love.huhu.platform.dto.UserLoginDto;
import love.huhu.platform.service.UserService;
import love.huhu.platform.service.dto.SolutionDto;
import love.huhu.platform.service.dto.TestDto;
import love.huhu.platform.utils.RedisUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

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

    public String getToken() {
        return token;
    }

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
        return JSONUtil.parseObj(response);
    }
     /**
     * 系统登录接口
     */
    public JSONObject userLogin(UserLoginDto userLoginDto) {
        return login(userLoginDto);
    }


    public JSONObject getGraphicCode() {
        String response = managerGet("/auth/code")
                .execute().body();

        return JSONUtil.parseObj(response);
    }
    public JSONObject getAnswerRecords(Long testId, Long problemId, Long studentId,Long answerRecordId) {
        String response = managerGet("/api/answerRecord")
                .form("testId",testId)
                .form("problemId", problemId)
                .form("userId", studentId)
                .form("id", answerRecordId)
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
                .form("id",solutionId)
                .execute().body();
        return JSONUtil.parseObj(response);
    }
    public JSONObject saveSolution(SolutionDto solution) {
        String response = managerPost("/api/solution")
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
    public JSONObject updateTest(TestDto test) {
        String response = managerPut("/api/test")
                .body(JSONUtil.toJsonStr(test))
                .execute().body();
        return null;
    }
    public JSONObject deleteTests(Long[] testIds) {
        String response = managerDel("/api/test")
                .body(Arrays.toString(testIds))
                .execute().body();
        return null;
    }

    public JSONObject getExecuteResult() {
        String response = managerGet("/api/executeResult")
                .execute().body();
        return JSONUtil.parseObj(response);
    }

    public JSONObject getProblem(Long problemId) {
        String response = managerGet("/api/problem")
                .form("id",problemId)
                .execute().body();
        return JSONUtil.parseObj(response);
    }

    public JSONObject getMyTest(Long userId, Long testId) {
        String response = managerGet("/api/test")
                .form("userId",userId)
                .form("id",testId)
                .execute().body();
        JSONArray array = JSONUtil.parseObj(response).getJSONArray("content");
        if (array.toArray().length != 0) {
            return JSONUtil.parseObj(response);
        }
        return null;
    }
    public JSONObject getTestByIds(List<Long> testIds) {
        String response = managerGet("/api/test")
                .form("ids",testIds)
                .execute().body();
        return JSONUtil.parseObj(response);
    }
    public JSONArray getTestByIdsWithoutPage(List<Long> testIds) {
        String response = managerGet("/api/test/all")
                .form("id",testIds)
                .execute().body();
        return JSONUtil.parseArray(response);
    }

    public List<JudgeMachine> getEnabledJudgeMachine() {
        String response = managerGet("/api/judgeMachine/online")
//                .form("enabled", true)
                .execute().body();
        return JSONUtil.toList(response, JudgeMachine.class);
    }

    public JSONObject getDept(String name, Boolean enabled, Long pid, Boolean pidIsNull) {
        String response = managerGet("/api/dept")
                .form("name", name)
                .form("enabled", enabled)
                .form("pid", pid)
                .form("pidIsNull", pidIsNull)
                .execute().body();
        return JSONUtil.parseObj(response);
    }

    public Object userLogout(String token) {
        if (!HttpRequest.delete(managerServerApi + "/auth/logout")
                .header(properties.getHeader(),token)
                .execute().isOk()) {
            throw new RuntimeException("退出失败");
        }
        return null;
    }

    public JSONObject getUserInfo(String token) {
        String response = HttpRequest.get(managerServerApi + "/auth/info")
                .header(properties.getHeader(), token)
                .execute().body();
        return JSONUtil.parseObj(response);
    }

    public JSONArray getLabelList() {
        String response = managerGet("/api/label/all")
                .execute().body();
        return JSONUtil.parseArray(response);
    }
}
