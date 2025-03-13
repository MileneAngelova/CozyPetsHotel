package bg.softuni.cozypetshotel.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "bookings.api")
public class BookingApiConfig {
    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }

    public BookingApiConfig setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }
}
