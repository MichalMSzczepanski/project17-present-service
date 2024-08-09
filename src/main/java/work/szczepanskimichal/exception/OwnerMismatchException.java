package work.szczepanskimichal.exception;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class OwnerMismatchException extends RuntimeException {

    public OwnerMismatchException(UUID userContextId, UUID ownerId) {
        super(String.format("user logged in as: %s, attempted to save an entity for owner: %s", userContextId, ownerId));
        log.error("user logged in as: {}, attempted to save an entity for owner: {}", userContextId, ownerId);
    }
}