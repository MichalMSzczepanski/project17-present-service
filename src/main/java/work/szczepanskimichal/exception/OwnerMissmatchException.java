package work.szczepanskimichal.exception;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class OwnerMissmatchException extends RuntimeException {

    public OwnerMissmatchException(UUID userContextId, UUID ownerId) {
        super(String.format("user logged in as: %s, attempted to save an entity for owner: %s", userContextId, ownerId));
        log.error(String.format("user logged in as: %s, attempted to save an entity for owner: %s", userContextId, ownerId));
    }
}