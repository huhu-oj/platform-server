package love.huhu.platform.authorization;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author nwl20
 * @create 2022/9/29 10:28
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthorizationRequired {
    @AliasFor("permission")
    PermissionEnum value() default PermissionEnum.ANY;
    @AliasFor("value")
    PermissionEnum permission() default PermissionEnum.ANY;
}
