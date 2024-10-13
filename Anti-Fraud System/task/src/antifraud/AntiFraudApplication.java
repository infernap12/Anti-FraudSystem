package antifraud;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

@SpringBootApplication
public class AntiFraudApplication {
    public static void main(String[] args) {
        final SpringApplication springApplication = new SpringApplication(AntiFraudApplication.class);
        springApplication.setBanner(new CustomBanner(new ClassPathResource("notBanner.txt")));
        springApplication.run(args);
    }
}