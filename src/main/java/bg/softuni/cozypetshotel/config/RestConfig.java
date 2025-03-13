package bg.softuni.cozypetshotel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class RestConfig {
    @Bean("bookingsRestClient")
            public RestClient bookingsrestClient(BookingApiConfig bookingApiConfig) {
        return RestClient.builder()
                .baseUrl(bookingApiConfig.getBaseUrl())
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
//                .requestInterceptor()
                .build();
            }
}
