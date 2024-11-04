package work.szczepanskimichal.service.s3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import work.szczepanskimichal.exception.CustomS3Exception;
import work.szczepanskimichal.exception.FileNotFoundException;
import work.szczepanskimichal.exception.InvalidFileException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${cloud.aws.region.static}")
    private String awsRegion;

    @Value("${custom.environment}")
    private String environment;

    @Value("${custom.max.file.size}")
    private long maxFileSize;

    @Value("#{'${custom.allowed.file.types}'.split(',')}")
    private List<String> allowedFileTypes;

    //todo return link to aws
    public byte[] getImage(String fileName, FileType type) {
        var fullFileName = type.getName() + fileName;
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fullFileName)
                    .build();
            return s3Client.getObjectAsBytes(getObjectRequest).asByteArray();
        } catch (NoSuchKeyException e) {
            throw new FileNotFoundException(fullFileName, bucketName);
        } catch (S3Exception e) {
            throw new CustomS3Exception("Error encountered during image fetching.", e.awsErrorDetails().errorMessage());
        }
    }

    public String uploadImage(MultipartFile file, FileType type) {
        checkIfBucketExists();

        var contentType = file.getContentType();

        if (file.getSize() > maxFileSize) {
            throw new InvalidFileException("File size exceeds the limit.");
        }

        if (!allowedFileTypes.contains(contentType)) {
            throw new InvalidFileException("Only allowed image types are JPG and PNG.");
        }

        var key = UUID.randomUUID().toString();
        var keyName = type.getName() + key;

        var putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .contentType(contentType)
                .build();

        try (var inputStream = file.getInputStream()) {
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, file.getSize()));
            return getRegularUrl(keyName); // Return regular URL instead of presigned URL
        } catch (S3Exception e) {
            throw new CustomS3Exception("Error encountered during image upload.", e.awsErrorDetails().errorMessage());
        } catch (IOException e) {
            throw new RuntimeException("Error getting input stream from file: " + e.getMessage());
        }
    }

    public void deleteImage(String fileName, FileType type) {
        getImage(fileName, type);
        var fullFileName = type.getName() + fileName;
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fullFileName)
                    .build();
            s3Client.deleteObject(deleteObjectRequest);
        } catch (NoSuchKeyException e) {
            throw new FileNotFoundException(fullFileName, bucketName);
        } catch (S3Exception e) {
            throw new CustomS3Exception("Error encountered during image deletion.", e.awsErrorDetails().errorMessage());
        }
    }

    private void checkIfBucketExists() {
        HeadBucketRequest headBucketRequest = HeadBucketRequest.builder()
                .bucket(bucketName)
                .build();
        try {
            s3Client.headBucket(headBucketRequest);
        } catch (NoSuchBucketException e) {
            log.info("Bucket {} does not exist. Creating a new bucket.", bucketName);
            CreateBucketRequest createBucketRequest;
            if ("prod".equals(environment)) {
                createBucketRequest = CreateBucketRequest.builder()
                        .bucket(bucketName)
                        .createBucketConfiguration(CreateBucketConfiguration.builder()
                                .locationConstraint(Region.of(awsRegion).id())
                                .build())
                        .build();
            } else {
                createBucketRequest = CreateBucketRequest.builder()
                        .bucket(bucketName)
                        .build();
            }
            try {
                s3Client.createBucket(createBucketRequest);
                log.info("Bucket {} created successfully.", bucketName);
            } catch (S3Exception ex) {
                throw new CustomS3Exception("Error encountered during bucket creation.", ex.awsErrorDetails().errorMessage());
            }
        } catch (S3Exception ex) {
            throw new CustomS3Exception("Error checking if bucket exists.", ex.awsErrorDetails().errorMessage());
        }
    }

    public String getRegularUrl(String fileName) {
        String url;

        if ("local".equalsIgnoreCase(environment)) {
            url = String.format("http://localhost:4566/%s/%s", bucketName, fileName);
        } else {
            url = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, awsRegion, fileName);
        }

        return url;
    }
}
