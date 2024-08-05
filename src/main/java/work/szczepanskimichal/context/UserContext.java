package work.szczepanskimichal.context;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.UUID;

@Component
@RequestScope
@Getter
@Setter
public class UserContext {

    private UUID userId;

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

}
