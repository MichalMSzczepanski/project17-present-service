package work.szczepanskimichal.service.assembler;

import work.szczepanskimichal.model.present.PresentCreateDto;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class PresentAssembler {

    public static PresentCreateDto assemblePresentCreateDto(String name,
                                                            String description,
                                                            UUID occasionId) {
        return PresentCreateDto.builder()
                .owner(UUID.fromString("989fc7f9-9c4e-4fca-b96c-603aea01909c"))
                .name(name)
                .description(description)
                .price(BigDecimal.valueOf(11L))
                .occasionId(occasionId)
                .build();
    }
}
