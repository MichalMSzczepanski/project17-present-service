package work.szczepanskimichal.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MissingHeadersException extends RuntimeException {

    public MissingHeadersException() {
        super("request has empty headers");
        log.error("request has empty headers");
    }
}