package work.szczepanskimichal.service;

import work.szczepanskimichal.model.occasion.Occasion;
import work.szczepanskimichal.model.person.Person;
import work.szczepanskimichal.model.person.PersonCreateDto;
import work.szczepanskimichal.model.present.Present;

import java.util.Set;
import java.util.UUID;

abstract class PersonAssembler {

    static PersonCreateDto assemblePersonCreateDto(String personName, String personLastName) {
        return PersonCreateDto.builder()
                .owner(UUID.randomUUID())
                .name(personName)
                .lastname(personLastName)
                .build();
    }

    static Person assemblePersonCreateDto(String name,
                                          String lastName,
                                          Set<Occasion> occasions,
                                          Set<Present> presents) {
        return Person.builder()
                .owner(UUID.randomUUID())
                .name(name)
                .lastname(lastName)
                .occasions(occasions)
                .build();
    }

}
