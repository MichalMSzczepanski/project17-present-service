//package work.szczepanskimichal.service;
//
//import work.szczepanskimichal.model.Occasion;
//import work.szczepanskimichal.model.Person;
//import work.szczepanskimichal.model.Present;
//
//import java.util.Set;
//import java.util.UUID;
//
//abstract class PersonAssembler {
//
//    static Person assemblePerson(String personName, String personLastName) {
//        return Person.builder()
//                .owner(UUID.randomUUID())
//                .name(personName)
//                .lastname(personLastName)
//                .build();
//    }
//
//    static Person assemblePerson(String name,
//                                 String lastName,
//                                 Set<Occasion> occasions,
//                                 Set<Present> presents) {
//        return Person.builder()
//                .owner(UUID.randomUUID())
//                .name(name)
//                .lastname(lastName)
//                .occasions(occasions)
//                .presents(presents)
//                .build();
//    }
//
//}
