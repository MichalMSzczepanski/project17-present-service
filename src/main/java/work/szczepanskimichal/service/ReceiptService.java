package work.szczepanskimichal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import work.szczepanskimichal.service.s3.FileType;
import work.szczepanskimichal.service.s3.S3Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class ReceiptService {

    private final S3Service s3Service;

    public String createReceiptImage(MultipartFile file) {
        return s3Service.uploadImage(file, FileType.RECEIPT);
    }

    public byte[] getReceiptImage(String fileName) {
        return s3Service.getImage(fileName, FileType.RECEIPT);
    }

    public void deleteReceiptImage(String fileName) {
        s3Service.deleteImage(fileName, FileType.RECEIPT);
    }

}
