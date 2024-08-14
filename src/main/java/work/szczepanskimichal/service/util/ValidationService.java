package work.szczepanskimichal.service.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.context.UserContext;
import work.szczepanskimichal.exception.OwnerMismatchException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ValidationService {

    public void validateOwner(UUID personOwnerId, UserContext userContext) {
        if (personOwnerId == userContext.getUserId()) {
            throw new OwnerMismatchException(userContext.getUserId(), personOwnerId);
        }
    }
}
