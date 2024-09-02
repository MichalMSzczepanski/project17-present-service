package work.szczepanskimichal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import work.szczepanskimichal.configuration.ServiceAddressConfiguration;

@SpringBootApplication
@EnableScheduling
@EnableFeignClients
@EnableConfigurationProperties({ServiceAddressConfiguration.class})
public class PresentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PresentServiceApplication.class, args);
    }
}
