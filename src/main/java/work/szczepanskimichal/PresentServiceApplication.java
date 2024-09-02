package work.szczepanskimichal;

import feign.codec.Decoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
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
    private final ObjectFactory<HttpMessageConverters> messageConverters = HttpMessageConverters::new;

//    @Bean
//    SpringFormEncoder feignFormEncoder() {
//        return new SpringFormEncoder(new SpringEncoder(messageConverters));
//    }
//
//    @Bean
//    Decoder feignFormDecoder() {
//        return new SpringDecoder(messageConverters);
//    }


}
