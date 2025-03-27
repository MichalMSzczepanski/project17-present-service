package work.szczepanskimichal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.model.present.PresentIdea;
import work.szczepanskimichal.model.present.PresentPurchased;
import work.szczepanskimichal.model.present.PresentPurchasedDto;
import work.szczepanskimichal.repository.PresentPurchasedRepository;

@Service
@RequiredArgsConstructor
public class PresentConverter {

    private final PersonService personService;
    private final ReceiptService receiptService;
    private final PresentPurchasedRepository presentPurchasedRepository;
    private final OccasionService occasionService;

    PresentPurchasedDto convertToPresentPurchased(PresentIdea presentIdea) {
        var person = personService.getPersonById(presentIdea.getOccasion().getPerson().getId());
        var owner = presentIdea.getOwner();

        PresentPurchased presentPurchased = PresentPurchased.builder()
                .owner(owner)
                .name(presentIdea.getName())
                .description(presentIdea.getDescription())
                .price(presentIdea.getPrice())
                .personId(person.getId())
                .build();

        var entity = presentPurchasedRepository.saveAndFlush(presentPurchased);

        var receiptOptional = receiptService.getReceiptByPresentIdea(presentIdea.getId());
        if (receiptOptional.isPresent()) {
            var receipt = receiptOptional.get();
            var updatedReceipt = receipt.toBuilder()
                    .presentIdea(null)
                    .presentPurchased(entity)
                    .build();
            receiptService.updateReceiptAfterPresentConversion(updatedReceipt);
        }

        return PresentPurchasedDto.builder()
                .id(entity.getId())
                .owner(entity.getOwner())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .personId(entity.getPersonId())
                .build();
    }

}
