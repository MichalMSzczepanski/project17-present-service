package work.szczepanskimichal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.exception.DataNotFoundException;
import work.szczepanskimichal.mapper.ReceiptMapper;
import work.szczepanskimichal.model.present.receipt.ReceiptCreateDto;
import work.szczepanskimichal.model.present.receipt.ReceiptDto;
import work.szczepanskimichal.model.present.receipt.ReceiptImageUpdateDto;
import work.szczepanskimichal.model.present.receipt.ReceiptUpdateDto;
import work.szczepanskimichal.repository.ReceiptRepository;
import work.szczepanskimichal.service.s3.FileType;
import work.szczepanskimichal.service.s3.S3Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final ReceiptMapper receiptMapper;
    private final S3Service s3Service;

    public ReceiptDto createReceipt(ReceiptCreateDto dto) {
        var currentPresentOptional = receiptRepository.findByPresentId(dto.getPresentId());
        if (currentPresentOptional.isPresent()) {
            s3Service.deleteImage(dto.getAwsS3Url(), FileType.RECEIPT);
            receiptRepository.deleteById(currentPresentOptional.get().getId());
        }
        String s3link = s3Service.uploadImage(dto.getImage(), FileType.RECEIPT);
        dto = dto.toBuilder().awsS3Url(s3link).build();
        try {
            var receipt = receiptRepository.save(receiptMapper.toEntity(dto));
            return receiptMapper.toDto(receipt);
        } catch (Exception e) {
            s3Service.deleteImage(s3link, FileType.RECEIPT);
            throw e;
        }
    }

    public ReceiptDto getReceipt(UUID receiptId) {
        var receipt = receiptRepository.findById(receiptId).orElseThrow(() -> new DataNotFoundException(receiptId));
        return receiptMapper.toDto(receipt);
    }

    public ReceiptDto updateReceipt(ReceiptUpdateDto receiptDto) {
        var receipt = receiptMapper.toEntity(receiptDto);
        var receiptUpdated = receiptRepository.save(receipt);
        return receiptMapper.toDto(receiptUpdated);
    }

    public ReceiptDto updateReceiptImage(ReceiptImageUpdateDto dto) {
        var receipt = receiptRepository.findById(dto.getId()).orElseThrow(() -> new DataNotFoundException(dto.getId()));
        s3Service.deleteImage(receipt.getAwsS3Url(), FileType.RECEIPT);
        var updatedS3link = s3Service.uploadImage(dto.getImage(), FileType.RECEIPT);
        receipt = receipt.toBuilder().awsS3Url(updatedS3link).build();
        var receiptUpdated = receiptRepository.save(receipt);
        return receiptMapper.toDto(receiptUpdated);
    }

    public void deleteReceipt(UUID receiptId) {
        var receipt = receiptRepository.findById(receiptId).orElseThrow(() -> new DataNotFoundException(receiptId));
        s3Service.deleteImage(receipt.getAwsS3Url(), FileType.RECEIPT);
    }
}
