package love.huhu.platform.config;

import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import love.huhu.platform.config.porperties.AuthorizationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;

/**
 * @Author nwl20
 * @create 2022/9/22 8:41
 */
@Configuration
public class ConfigBeanConfiguration {

    @Bean
    @ConfigurationProperties("jwt")
    public AuthorizationProperties authorizationProperties() {
        return new AuthorizationProperties();
    }

    @Bean
    public TemplateEngine templateEngine() {
        TemplateConfig config = new TemplateConfig(StandardCharsets.UTF_8,"templates", TemplateConfig.ResourceMode.CLASSPATH);
        return TemplateUtil.createEngine(config);
    }
}
