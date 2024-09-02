package work.szczepanskimichal.model.user;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder(toBuilder = true)
@Getter
public class UserCommsDto {

    private UUID id;
    private String email;
    private String phoneNumber;

}