package bg.softuni.cozypetshotel.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BeanConfigurations {
    @Bean
    public ModelMapper modelMapper()                                                                                                {
        return new ModelMapper();
    }
}
