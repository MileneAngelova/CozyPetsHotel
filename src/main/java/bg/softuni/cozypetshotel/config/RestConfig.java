package bg.softuni.cozypetshotel.config;

import bg.softuni.cozypetshotel.services.JwtService;
import bg.softuni.cozypetshotel.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Configuration
public class RestConfig {

    @Bean("genericRestClient")
    public RestClient genericRestClient() {
        return RestClient.create();
    }

    @Bean("bookingsRestClient")
            public RestClient bookingsrestClient(BookingApiConfig bookingApiConfig, ClientHttpRequestInterceptor requestInterceptor) {
        return RestClient.builder()
                .baseUrl(bookingApiConfig.getBaseUrl())
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .requestInterceptor(requestInterceptor)
                .build();
            }

    @Bean
    public ClientHttpRequestInterceptor requestInterceptor(UserService userService, JwtService jwtService) {
        return (r, b, e) -> {
            // put the logged user details into bearer token
            userService
                    .getCurrentUser()
                    .ifPresent(mud -> {
                        String bearerToken = jwtService.generateToken(
                                mud.getUuid().toString(),
                                Map.of(
                                        "roles",
                                        mud.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()
                                )
                        );

                        r.getHeaders().setBearerAuth(bearerToken);
                    });

            return e.execute(r, b);
        };
    }
}
