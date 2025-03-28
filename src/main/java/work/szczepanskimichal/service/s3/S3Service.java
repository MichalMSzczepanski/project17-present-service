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
import java.util.ArrayList;
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

    public String uploadImage(MultipartFile file, FileType type, UUID id) {
        checkIfBucketExists();

        var contentType = file.getContentType();

        //todo extract
        if (file.getSize() > maxFileSize) {
            throw new InvalidFileException("File size exceeds the limit.");
        }

        //todo extract
        if (!allowedFileTypes.contains(contentType)) {
            throw new InvalidFileException("Only allowed image types are JPG and PNG.");
        }

        var key = id;
        var fullKeyName = type.getName() + key;

        var putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fullKeyName)
                .contentType(contentType)
                .build();

        try (var inputStream = file.getInputStream()) {
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, file.getSize()));
            return getAwsS3Url(fullKeyName);
        } catch (S3Exception e) {
            throw new CustomS3Exception("Error encountered during image upload.", e.awsErrorDetails().errorMessage());
        } catch (IOException e) {
            throw new RuntimeException("Error getting input stream from file: " + e.getMessage());
        }
    }

    public void deleteImage(String fileName, FileType type) {
        checkIfImageExists(fileName);
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

    public List<String> fetchKeysWithPrefix(String bucketName, String prefix) {
        List<String> filteredKeys = new ArrayList<>();
        String continuationToken = null;

        do {
            ListObjectsV2Request request = ListObjectsV2Request.builder()
                    .bucket(bucketName)
                    .prefix(prefix)
                    .continuationToken(continuationToken)
                    .build();

            ListObjectsV2Response response = s3Client.listObjectsV2(request);
            for (S3Object s3Object : response.contents()) {
                filteredKeys.add(s3Object.key());
            }

            continuationToken = response.isTruncated() ? response.nextContinuationToken() : null;
        } while (continuationToken != null);

        return filteredKeys;
    }

    private void checkIfImageExists(String fileName) {
        var fullFileName = fileName.replaceFirst(".*/receipt/", "receipt/");
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fullFileName)
                    .build();
            s3Client.getObjectAsBytes(getObjectRequest).asByteArray();
        } catch (NoSuchKeyException e) {
            throw new FileNotFoundException(fullFileName, bucketName);
        } catch (S3Exception e) {
            throw new CustomS3Exception("Error encountered during image fetching.", e.awsErrorDetails().errorMessage());
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

    private String getAwsS3Url(String fileName) {
        if ("local".equalsIgnoreCase(environment)) {
            return String.format("http://localhost:4566/%s/%s", bucketName, fileName);
        } else {
            return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, awsRegion, fileName);
        }
    }

//    public String getImageRegularUrl(String fileName, FileType type) {
//        var fullFileName = type.getName() + fileName;
//        try {
//            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
//                    .bucket(bucketName)
//                    .key(fullFileName)
//                    .build();
//
//            s3Client.getObject(getObjectRequest);
//
//            return getAwsS3Url(fullFileName);
//        } catch (NoSuchKeyException e) {
//            throw new FileNotFoundException(fullFileName, bucketName);
//        } catch (S3Exception e) {
//            throw new CustomS3Exception("Error encountered during image fetching.", e.awsErrorDetails().errorMessage());
//        }
//    }
}
