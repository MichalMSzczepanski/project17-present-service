package work.szczepanskimichal.model.person;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder(toBuilder = true)
@Getter
public class PersonCreateDto {

    private UUID owner;
    private String name;
    private String lastname;

}
