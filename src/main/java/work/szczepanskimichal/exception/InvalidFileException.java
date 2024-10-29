package work.szczepanskimichal.exception;

public class InvalidFileException extends RuntimeException {

    public InvalidFileException(String fieldType) {
        super(String.format("Invalid file. Issue: %s", fieldType));
    }
}