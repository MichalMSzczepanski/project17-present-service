package work.szczepanskimichal.exception;

public class CustomS3Exception extends RuntimeException {

    public CustomS3Exception(String comment, String message) {
        super(String.format("S3 exception encountered. Comment: %s. Error message: %s", comment, message));
    }

}