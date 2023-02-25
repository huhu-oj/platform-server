package love.huhu.platform.authorization;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import love.huhu.platform.client.ManagerClient;
import love.huhu.platform.config.porperties.AuthorizationProperties;
import love.huhu.platform.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * @Author nwl20
 * @create 2022/9/29 15:02
 */
@Component
@AllArgsConstructor
@Slf4j
public class AuthorizationHandler {

    private AuthorizationProperties properties;
    private final ManagerClient managerClient;
    public boolean handleIdentity(HttpServletRequest request) {
        String token = getTokenHeader(request);
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        // todo 尝试将返回布尔值全都换成异常处理
        if (!token.startsWith(properties.getTokenStartWith())) {
            return false;
        }
        token = token.substring(token.indexOf(" ")+1);
        log.debug("切割token：{}",token);
        JWTValidator.of(token).validateDate();

        //解析token
        JWT jwt = JWTUtil.parseToken(token);
        Long userId = Integer.toUnsignedLong((Integer) jwt.getPayload("userId"));
        User user = getUserInfo(userId);

        //放入threadlocal
        UserHolder.setUser(user);
        return true;
    }

    private User getUserInfo(Long userId) {
        User user = managerClient.getUserById(userId);
        return user;
    }

    private String getTokenHeader(HttpServletRequest request) {
        String authorization = request.getHeader(properties.getHeader());
        return authorization;
    }

    public boolean handleAuth(PermissionEnum permission) {
        return false;
    }
}
