package work.szczepanskimichal.model;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class PersonCreatedDto {

    private UUID id;
    private UUID owner;
    private String name;
    private String lastname;

}
