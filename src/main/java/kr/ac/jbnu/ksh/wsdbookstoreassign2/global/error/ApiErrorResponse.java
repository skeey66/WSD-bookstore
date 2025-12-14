package kr.ac.jbnu.ksh.wsdbookstoreassign2.global.error;

import java.time.Instant;
import java.util.Map;

public record ApiErrorResponse(
        Instant timestamp,
        String path,
        int status,
        String code,
        String message,
        Map<String, Object> details
) {}
