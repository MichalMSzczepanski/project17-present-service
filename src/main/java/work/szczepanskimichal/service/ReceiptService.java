package work.szczepanskimichal.service;

import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.exception.DataNotFoundException;
import work.szczepanskimichal.mapper.ReceiptMapper;
import work.szczepanskimichal.model.present.receipt.*;
import work.szczepanskimichal.repository.ReceiptRepository;
import work.szczepanskimichal.service.s3.FileType;
import work.szczepanskimichal.service.s3.S3Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final ReceiptMapper receiptMapper;
    private final S3Service s3Service;

    public ReceiptDto createReceipt(ReceiptCreateDto dto) {
        //todo instead of this check, just overwrite whatever is in the s3
        deletePotentialReceiptImageFromAWS(dto);
        var s3link = s3Service.uploadImage(dto.getImage(), FileType.RECEIPT);
        dto = dto.toBuilder().imageUrl(s3link).build();
        try {
            var receiptEntity = receiptMapper.toEntity(dto);
            var receipt = receiptRepository.save(receiptEntity);
            return receiptMapper.toDto(receipt);
        } catch (DataAccessException | PersistenceException e) {
            s3Service.deleteImage(s3link, FileType.RECEIPT);
            throw new RuntimeException("Error occurred while saving receipt and uploading image.", e);
        }
    }

    public ReceiptDto getReceipt(UUID receiptId) {
        var receipt = receiptRepository.findById(receiptId).orElseThrow(() -> new DataNotFoundException(receiptId));
        return receiptMapper.toDto(receipt);
    }

    public Optional<Receipt> getReceiptByPresentIdea(UUID presentIdeaId) {
        return receiptRepository.findByPresentIdeaId(presentIdeaId);
    }

    public ReceiptDto updateReceipt(ReceiptUpdateDto receiptDto) {
        var receipt = receiptMapper.toEntity(receiptDto);
        var receiptUpdated = receiptRepository.save(receipt);
        return receiptMapper.toDto(receiptUpdated);
    }

    public void updateReceiptAfterPresentConversion(Receipt receipt) {
        receiptRepository.saveAndFlush(receipt);
    }

    public ReceiptDto updateReceiptImage(ReceiptImageUpdateDto dto) {
        var receipt = receiptRepository.findById(dto.getId()).orElseThrow(() -> new DataNotFoundException(dto.getId()));
        s3Service.deleteImage(receipt.getImageUrl(), FileType.RECEIPT);
        var updatedS3link = s3Service.uploadImage(dto.getImage(), FileType.RECEIPT);
        receipt = receipt.toBuilder().imageUrl(updatedS3link).build();
        var receiptUpdated = receiptRepository.save(receipt);
        return receiptMapper.toDto(receiptUpdated);
    }

    public void deleteReceipt(UUID receiptId) {
        var receipt = receiptRepository.findById(receiptId).orElseThrow(() -> new DataNotFoundException(receiptId));
        s3Service.deleteImage(receipt.getImageUrl(), FileType.RECEIPT);
    }

    private void deletePotentialReceiptImageFromAWS(ReceiptCreateDto dto) {
        var currentPresentOptional = receiptRepository.findByPresentIdeaId(dto.getPresentId());
        currentPresentOptional.ifPresent(receipt -> {
            var aws3url = receipt.getImageUrl();
            s3Service.deleteImage(aws3url, FileType.RECEIPT);
            receiptRepository.deleteById(receipt.getId());
        });
    }
}
