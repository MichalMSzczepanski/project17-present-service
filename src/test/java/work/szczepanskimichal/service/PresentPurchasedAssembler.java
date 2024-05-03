package work.szczepanskimichal.service;

import work.szczepanskimichal.model.PresentPurchased;

import java.math.BigDecimal;

abstract class PresentPurchasedAssembler {

    static PresentPurchased assemblePresentIdea(String name, String description) {
        return PresentPurchased.builder()
                .name(name)
                .description(description)
                .price(BigDecimal.valueOf(99L))
                .build();
    }
}
