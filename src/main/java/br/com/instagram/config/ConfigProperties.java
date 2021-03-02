package br.com.instagram.config;

import br.com.instagram.util.BeanUtil;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ConfigProperties {
    @Value("${api.basePath}")
    private String apiBasePath;

    public static String getApiBasePath() {
        ConfigProperties bean = BeanUtil.getBean(ConfigProperties.class);
        return bean.apiBasePath;
    }
}
