package work.szczepanskimichal.service.assembler;

import work.szczepanskimichal.model.person.PersonCreateDto;

import java.util.UUID;

public abstract class PersonAssembler {

    public static PersonCreateDto assemblePersonCreateDto(String personName, String personLastName) {
        return PersonCreateDto.builder()
                .owner(UUID.randomUUID())
                .name(personName)
                .lastname(personLastName)
                .build();
    }
}
