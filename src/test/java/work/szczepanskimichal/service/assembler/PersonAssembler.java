package work.szczepanskimichal.service.assembler;

import work.szczepanskimichal.model.person.PersonCreateDto;

import java.util.UUID;

public abstract class PersonAssembler {

    public static PersonCreateDto assemblePersonCreateDto(String personName, String personLastName) {
        return PersonCreateDto.builder()
                .owner(UUID.fromString("989fc7f9-9c4e-4fca-b96c-603aea01909c"))
                .name(personName)
                .lastname(personLastName)
                .build();
    }
}
