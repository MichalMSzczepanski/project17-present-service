package work.szczepanskimichal.service;

import work.szczepanskimichal.model.PresentPurchased;

import java.math.BigDecimal;
import java.util.UUID;

abstract class PresentPurchasedAssembler {

    static PresentPurchased assemblePresentIdea(String name, String description) {
        return PresentPurchased.builder()
                .owner(UUID.randomUUID())
                .name(name)
                .description(description)
                .price(BigDecimal.valueOf(99L))
                .build();
    }
}
