package work.szczepanskimichal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import work.szczepanskimichal.model.present.receipt.Receipt;
import work.szczepanskimichal.model.present.receipt.ReceiptCreateDto;
import work.szczepanskimichal.model.present.receipt.ReceiptDto;
import work.szczepanskimichal.model.present.receipt.ReceiptUpdateDto;

@Mapper(componentModel = "spring")
public abstract class ReceiptMapper {

    @Mapping(target = "presentIdea.id", source = "presentId")
    public abstract Receipt toEntity(ReceiptCreateDto receiptCreateDto);

    @Mapping(target = "presentIdea.id", source = "presentId")
    public abstract Receipt toEntity(ReceiptUpdateDto reminderUpdateDto);

    @Mapping(target = "presentId", source = "presentIdea.id")
    public abstract ReceiptDto toDto(Receipt reminder);

}
