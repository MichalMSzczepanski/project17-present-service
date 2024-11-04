package work.szczepanskimichal.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

@Configuration
public class AwsS3Config {

    @Value("${custom.environment}")
    private String environment;

    @Value("${cloud.aws.region.static}")
    private String awsRegion;

    @Bean
    public S3Client s3Client() {
        var s3ClientBuilder = S3Client.builder()
                .region(Region.of(awsRegion))
                .serviceConfiguration(S3Configuration.builder().build())
                .forcePathStyle(true);

        if ("prod".equals(environment)) {
            s3ClientBuilder.credentialsProvider(DefaultCredentialsProvider.create());
        } else {
            s3ClientBuilder.endpointOverride(URI.create("http://localhost:4566"))
                    .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create("test", "test")));
        }

        return s3ClientBuilder.build();
    }
}