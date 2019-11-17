package com.heshi.audit.config;

import com.heshi.audit.entity.AuditLog;
import com.heshi.audit.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestControllerAdvice
public class ErrorHandler {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(Exception.class)
    public Map<String, Object> handler(HttpServletRequest request, Exception ex) {
        Map<String, Object> info = new HashMap<>();
        info.put("message", ex.getMessage());
        info.put("date", LocalDateTime.now());
        if (null != request) {
            Long auditLogId = (Long) request.getAttribute("auditLogId");
            Optional<AuditLog> optional = auditLogRepository.findById(auditLogId);
            if (optional.isPresent()) {
                AuditLog auditLog = optional.get();
                auditLog.setMsg(ex.getMessage());
                auditLogRepository.save(auditLog);
            }
        }
        return info;
    }
}
