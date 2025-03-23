package bg.softuni.cozypetshotel.config;

import bg.softuni.cozypetshotel.repositories.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class BeanConfigurations {
    @Bean
    public ModelMapper modelMapper()                                                                                                {
        return new ModelMapper();
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource,
                                                       RoleRepository roleRepository,
                                                       ResourceLoader resourceLoader) {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);

        if (roleRepository.count() == 0) {
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
            populator.addScript(resourceLoader.getResource("classpath:data.sql"));
            initializer.setDatabasePopulator(populator);
        }

        return initializer;
    }
}
