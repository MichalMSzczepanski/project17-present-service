package work.szczepanskimichal.service;

import work.szczepanskimichal.model.Present;

import java.math.BigDecimal;
import java.util.UUID;

abstract class PresentAssembler {

    static Present assemblePresentIdea(String name, String description) {
        return Present.builder()
                .owner(UUID.randomUUID())
                .name(name)
                .description(description)
                .price(BigDecimal.valueOf(11L))
                .build();
    }
}
