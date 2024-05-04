package work.szczepanskimichal.service;

import work.szczepanskimichal.model.Occasion;
import work.szczepanskimichal.model.Person;
import work.szczepanskimichal.model.PresentIdea;
import work.szczepanskimichal.model.PresentPurchased;

import java.util.Set;
import java.util.UUID;

abstract class PersonAssembler {

    static Person assemblePerson(String personName, String personLastName) {
        return Person.builder()
                .owner(UUID.randomUUID())
                .name(personName)
                .lastname(personLastName)
                .build();
    }

    static Person assemblePerson(String name,
                                 String lastName,
                                 Set<Occasion> occasions,
                                 Set<PresentIdea> presentIdeas,
                                 Set<PresentPurchased> presentsPurchased) {
        return Person.builder()
                .owner(UUID.randomUUID())
                .name(name)
                .lastname(lastName)
                .occasions(occasions)
                .presentsIdeas(presentIdeas)
                .presentsPurchased(presentsPurchased)
                .build();
    }

}
