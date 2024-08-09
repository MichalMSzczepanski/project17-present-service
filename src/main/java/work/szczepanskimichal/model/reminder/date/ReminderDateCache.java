package work.szczepanskimichal.model.reminder.date;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("ReminderDateCache")
public class ReminderDateCache implements Serializable {

    private static final long serialVersionUID = 7526472295622776147L;

    @Id
    private UUID id;

    @Indexed // Optional, for indexing if you plan to query by this field
    private UUID reminderId;

    private Date date;
}
