package love.huhu.platform.config;

import lombok.AllArgsConstructor;
import love.huhu.platform.interceptor.RequestInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author nwl20
 * @create 2022/9/29 17:05
 */
@AllArgsConstructor
@Component
public class WebMvcConfiguration implements WebMvcConfigurer {
    private RequestInterceptor requestInterceptor;
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
//                .allowedOrigins("http://localhost:8080")
                .allowedMethods("GET", "PUT", "DELETE", "POST", "OPTIONS")
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/auth/**")
                .excludePathPatterns("/swagger-resources/**")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/v2/**")
                .excludePathPatterns("/doc.html/**");
    }
}
