package work.szczepanskimichal.exception;

public class FileNotFoundException extends RuntimeException {

    public FileNotFoundException(String key, String bucket) {
        super(String.format("File %s not found in bucket %s", key, bucket));
    }

}