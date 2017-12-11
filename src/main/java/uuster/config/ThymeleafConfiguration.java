package uuster.config;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

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

        @Bean
        public TemplateEngine templateEngine() {
            TemplateEngine templateEngine = new TemplateEngine();
            templateEngine.addDialect(new Java8TimeDialect());
            return templateEngine;
        }
    }

}
