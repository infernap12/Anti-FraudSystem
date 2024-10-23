package antifraud.validation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    CardNumberValidator cardNumberValidator() {
        return new CardNumberValidator();
    }

    @Bean
    IpAddressValidator ipAddressValidator() {
        return new IpAddressValidator();
    }
}
