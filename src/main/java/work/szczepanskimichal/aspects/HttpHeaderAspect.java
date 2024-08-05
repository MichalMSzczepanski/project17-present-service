package work.szczepanskimichal.aspects;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import work.szczepanskimichal.context.UserContext;
import work.szczepanskimichal.exception.MissingHeaderException;
import work.szczepanskimichal.exception.MissingHeadersException;

import java.util.UUID;


@Aspect
@Component
public class HttpHeaderAspect {

    private static final String X_USER_ID = "x-user-id";

    @Autowired
    private UserContext userContext;

    @Before("execution(* work.szczepanskimichal.controller..*(..)) && " +
            "(execution(* *..*update*(..)) || execution(* *..*create*(..)))")
    public void checkForUserIdHeader() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new MissingHeadersException();
        }
        HttpServletRequest request = attributes.getRequest();
        String headerUserId = request.getHeader(X_USER_ID);
        if (headerUserId == null || headerUserId.isEmpty()) {
            throw new MissingHeaderException(X_USER_ID);
        }
        userContext.setUserId(UUID.fromString(headerUserId));
    }
}
