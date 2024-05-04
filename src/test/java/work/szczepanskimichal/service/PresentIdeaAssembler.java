package work.szczepanskimichal.service;

import work.szczepanskimichal.model.PresentIdea;

import java.math.BigDecimal;
import java.util.UUID;

abstract class PresentIdeaAssembler {

    static PresentIdea assemblePresentIdea(String name, String description) {
        return PresentIdea.builder()
                .owner(UUID.randomUUID())
                .name(name)
                .description(description)
                .price(BigDecimal.valueOf(11L))
                .build();
    }
}
