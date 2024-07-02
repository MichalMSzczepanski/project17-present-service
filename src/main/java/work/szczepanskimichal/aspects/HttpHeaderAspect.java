package work.szczepanskimichal.aspects;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import work.szczepanskimichal.exception.MissingHeaderException;


@Aspect
@Component
@Profile("prod")
public class HttpHeaderAspect {

    private static final String X_USER_ID = "X-User-ID";

    @Before("execution(* work.szczepanskimichal.controller..*(..))")
    public void checkForUserIdHeader(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new MissingHeaderException(X_USER_ID);
        }
        HttpServletRequest request = attributes.getRequest();
        String headerUserId = request.getHeader(X_USER_ID);
        if (headerUserId == null || headerUserId.isEmpty()) {
            throw new MissingHeaderException(X_USER_ID);
        }
    }
}
