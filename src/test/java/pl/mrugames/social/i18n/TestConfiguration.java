package pl.mrugames.social.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.io.IOException;

@Configuration
@ComponentScan("pl.mrugames.social")
public class TestConfiguration {

    @Bean
    MessageSource messageSource() throws IOException {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames("i18n");
        source.setDefaultEncoding("UTF-8");
        source.setFallbackToSystemLocale(false);

        return source;
    }

}
