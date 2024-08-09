package work.szczepanskimichal.exception;

import java.util.UUID;

public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException() {
        super(String.format("No data of type %s not found", getEntityName()));
    }

    public DataNotFoundException(UUID id) {
        super(String.format("%s with id %s not found", getEntityName(), id));
    }

    public DataNotFoundException(UUID id, String parentEntity) {
        super(String.format("%s not found for %s with id %s", getEntityName(), parentEntity, id));
    }

    private static String getEntityName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String fullClassName = stackTrace[3].getClassName();
        String className = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);

        if (className.endsWith("Service")) {
            className = className.substring(0, className.length() - "Service".length());
        }
        return className;
    }
}