package work.szczepanskimichal.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.service.s3.FileType;
import work.szczepanskimichal.service.s3.S3Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3CleanupScheduler {

    private final S3Service service;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Scheduled(cron = "0 0 0 * * ?")
    public void runCleanup() {
        List<String> receiptKeys = service.fetchKeysWithPrefix(bucketName, FileType.RECEIPT.getName());
        // check if in postgres in receipts table the keys from s3 are NOT IN the table
        // delete all that are not in the table
    }

}