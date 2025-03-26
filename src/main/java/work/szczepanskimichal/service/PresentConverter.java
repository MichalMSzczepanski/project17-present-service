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
        //fetch person - need it for presentPurchased relationship
        var occasion = occasionService.getOccasionById(presentIdea.getOccasion().getId());
        var person = personService.getPersonById(occasion.getPerson().getId());
        //todo check this manually if the person2 is fetched correctly, the test is kinda messed up
//        var person2 = personService.getPersonById(presentIdea.getOccasion().getPerson().getId());
        var owner = presentIdea.getOwner();

        //convert presentIdea to presentPurchased
        PresentPurchased presentPurchased = PresentPurchased.builder()
                .owner(owner)
                .name(presentIdea.getName())
                .description(presentIdea.getDescription())
                .price(presentIdea.getPrice())
                .personId(person.getId())
                //additional image of purchased present apart from receipt later
//                .image_url(image later)
                .build();

        //SAVE presentPurchased entity in DB
        var entity = presentPurchasedRepository.saveAndFlush(presentPurchased);

        //check if presentIdea has a receipt
        var receiptOptional = receiptService.getReceiptByPresentIdea(presentIdea.getId());
        if (receiptOptional.isPresent()) {
            //if receipt present, remove presentIdea field value, assign presentPurchased value and SAVE receipt entity in DB
            var receipt = receiptOptional.get();
            var updatedReceipt = receipt.toBuilder()
                    .presentIdea(null)
                    .presentPurchased(entity)
                    .build();
            receiptService.updateReceiptAfterPresentConversion(updatedReceipt);
        }

        //return presentPurchased assuming no errors
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
