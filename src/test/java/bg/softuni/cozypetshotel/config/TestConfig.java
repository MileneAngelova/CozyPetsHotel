package bg.softuni.cozypetshotel.config;

import bg.softuni.cozypetshotel.repositories.UserRepository;
import bg.softuni.cozypetshotel.services.UserService;
import bg.softuni.cozypetshotel.validation.UniqueEmailValidator;
import bg.softuni.cozypetshotel.web.TestController;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class TestConfig {
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        return new LocaleChangeInterceptor();
    }

    @Bean
    public TestController testController() {
        return new TestController();
    }

    @Bean
    public UniqueEmailValidator uniqueEmailValidator(UserRepository userRepository) {
        return new UniqueEmailValidator(userRepository);
    }

    @Bean
    public UserService userService() {
        return mock(UserService.class);
    }
}
