package bg.softuni.cozypetshotel.config;

import bg.softuni.cozypetshotel.repositories.UserRepository;
import bg.softuni.cozypetshotel.session.AppUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(
                        authorizeRequests -> authorizeRequests
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers("/user/account", "/book-now", "/bookings/user", "/bookings/user/delete/{id}", "/logout").authenticated()
                                .requestMatchers("/", "/gallery", "/services", "/info", "/prices", "/login", "/register", "/login-error").permitAll()
                                .requestMatchers("/admin/**", "/admin/users/delete", "/bookings/delete/{id}", "/bookings/**").hasRole("ADMIN")
                                .anyRequest()
                                .authenticated())
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/bookings/user", true)
                                .failureForwardUrl("/login-error"))

                .logout(logout ->
                        logout.logoutUrl("/logout")
                                .logoutSuccessUrl("/")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID"))
                .build();
    }

    @Bean
    public AppUserDetailsService userDetailsService(UserRepository userRepository) {
        return new AppUserDetailsService(userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }
}
