package work.szczepanskimichal.service;

import work.szczepanskimichal.model.person.PersonCreateDto;

import java.util.UUID;

abstract class PersonAssembler {

    static PersonCreateDto assemblePersonCreateDto(String personName, String personLastName) {
        return PersonCreateDto.builder()
                .owner(UUID.randomUUID())
                .name(personName)
                .lastname(personLastName)
                .build();
    }
}
