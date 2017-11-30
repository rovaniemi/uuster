package uuster.config;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThymeleafConfiguration {

    @Configuration
    @ConditionalOnClass(name = "nz.net.ultraq.thymeleaf.LayoutDialect")
    protected static class ThymeleafWebLayoutConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public LayoutDialect layoutDialect() {
            return new LayoutDialect();
        }

    }
}
