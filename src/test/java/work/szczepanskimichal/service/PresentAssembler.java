package work.szczepanskimichal.service;

import work.szczepanskimichal.model.present.PresentCreateDto;

import java.math.BigDecimal;
import java.util.UUID;

abstract class PresentAssembler {

    static PresentCreateDto assemblePresentIdea(String name,
                                                String description,
                                                UUID occasionId) {
        return PresentCreateDto.builder()
                .owner(UUID.randomUUID())
                .name(name)
                .description(description)
                .price(BigDecimal.valueOf(11L))
                .occasionId(occasionId)
                .build();
    }
}
