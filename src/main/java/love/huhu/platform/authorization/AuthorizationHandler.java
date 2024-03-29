package love.huhu.platform.authorization;

import cn.hutool.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import love.huhu.platform.client.ManagerClient;
import love.huhu.platform.config.porperties.AuthorizationProperties;
import love.huhu.platform.domain.Role;
import love.huhu.platform.domain.User;
import love.huhu.platform.service.UserService;
import love.huhu.platform.utils.RedisUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Set;

/**
 * @Author nwl20
 * @create 2022/9/29 15:02
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AuthorizationHandler {

    private final RedisUtils redisUtils;
    private final AuthorizationProperties properties;
    private final ManagerClient managerClient;
    private final UserService userService;

    public boolean handleIdentity(HttpServletRequest request) {
        String token = getTokenHeader(request);
        //开发配置
        if (properties.getSkipToken() && StringUtils.isEmpty(token)) {
            token = managerClient.getToken();
            if (StringUtils.isEmpty(token) || !redisUtils.hasKey(properties.getOnlineKey()+token)) {
                managerClient.systemLogin();
                token = managerClient.getToken();
            }
        }
        if (StringUtils.isEmpty(token)) {
            throw new RuntimeException("token为空");
        }
        if (!token.startsWith(properties.getTokenStartWith())) {
            throw new RuntimeException("token格式有误");
        }
        String tokenToSave = token;
        token = token.substring(token.indexOf(" ")+1);
        log.debug("切割token：{}",token);
        //校验token
        if (!redisUtils.hasKey(properties.getOnlineKey()+token)) {
            throw new RuntimeException("token过期");
        }
        //解析token
        String username = JWTUtil.parseToken(token).getPayload("user").toString();
        User user = null;
        while(user == null || user.getId() == null) {
            user = getUserByName(username);
            if (user == null || user.getId() == null) {
                managerClient.systemLogin();
            }
        }

        //放入threadlocal
        UserHolder.setUser(user,tokenToSave);
        return true;
    }

    private User getUserByName(String username) {
        User user = managerClient.getUserByName(username);
        return user;
    }
    private String getTokenHeader(HttpServletRequest request) {
        String authorization = request.getHeader(properties.getHeader());
        return authorization;
    }

    public boolean handleAuth(PermissionEnum[] permissions) {
        if (Arrays.asList(permissions).contains(PermissionEnum.ANY)) {
            return true;
        }
        //获取用户的角色
        Set<Role> roles = UserHolder.getUser().getRoles();
        return roles.stream().anyMatch(role ->
            Arrays.stream(permissions).anyMatch(permissionEnum -> permissionEnum.getRoleName().equalsIgnoreCase(role.getName())));
    }
}
