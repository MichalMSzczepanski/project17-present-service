package work.szczepanskimichal.model.reminder.date;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
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

//    @Indexed
    private UUID reminderId;

    private Date date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReminderDateCache that = (ReminderDateCache) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(reminderId, that.reminderId) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reminderId, date);
    }
}
