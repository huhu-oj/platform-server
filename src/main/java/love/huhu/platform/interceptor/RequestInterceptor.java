package love.huhu.platform.interceptor;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import love.huhu.platform.authorization.AuthorizationHandler;
import love.huhu.platform.authorization.AuthorizationRequired;
import love.huhu.platform.authorization.PermissionEnum;
import love.huhu.platform.authorization.UserHolder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Author nwl20
 * @create 2022/9/29 11:09
 */
@Component
@AllArgsConstructor
@Slf4j
public class RequestInterceptor implements HandlerInterceptor {
    private AuthorizationHandler authorizationHandler;
    private MappingJackson2HttpMessageConverter converter;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //  如果不是反射到方法
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //检查方法上的注解
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        AuthorizationRequired annotation = method.getAnnotation(AuthorizationRequired.class);
        if (annotation == null) {
            //不需要权限
            return true;
        }
        PermissionEnum permission = annotation.value();

        try {
            authorizationHandler.handleIdentity(request);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return false;
        }

        try {
            authorizationHandler.handleAuth(permission);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserHolder.remove();
    }
}
