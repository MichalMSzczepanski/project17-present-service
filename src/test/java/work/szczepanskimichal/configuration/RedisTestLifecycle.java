package work.szczepanskimichal.configuration;

import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import redis.embedded.RedisServer;

import java.io.IOException;

@Configuration
public class RedisTestLifecycle {
    private RedisServer redisServer;

    @Bean
    @Scope("singleton")
    public RedisServer redisServer() throws IOException {
        redisServer = new RedisServer(6379);
        redisServer.start();
        return redisServer;
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
            System.out.println("Redis server stopped");
        }
    }
}
