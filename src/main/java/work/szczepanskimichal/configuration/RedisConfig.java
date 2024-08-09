package work.szczepanskimichal.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import work.szczepanskimichal.model.reminder.date.ReminderDateCache;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, ReminderDateCache> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, ReminderDateCache> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory("localhost", 6379);
    }
}
