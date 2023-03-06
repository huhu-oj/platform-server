package love.huhu.platform.aspect;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import love.huhu.platform.client.ManagerClient;
import love.huhu.platform.utils.ThrowableUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author nwl
 * @Create 2023-03-05 下午4:06
 */

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class ManagerRequestAspect {

    private final ManagerClient managerClient;

    @Pointcut("execution(public * love.huhu.platform.client.ManagerClient.manager*(..))")
    public void pointcut() {
    }
    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        if (StrUtil.isBlank(managerClient.getToken())) {
            managerClient.systemLogin();
        }
    }
}
