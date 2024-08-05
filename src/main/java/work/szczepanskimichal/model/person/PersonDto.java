package work.szczepanskimichal.model.person;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class PersonDto {

    private UUID id;
    private UUID owner;
    private String name;
    private String lastname;

}
