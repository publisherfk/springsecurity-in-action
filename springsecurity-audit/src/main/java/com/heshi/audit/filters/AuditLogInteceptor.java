package com.heshi.audit.filters;

import com.heshi.audit.entity.AuditLog;
import com.heshi.audit.entity.UserDo;
import com.heshi.audit.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
public class AuditLogInteceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        AuditLog auditLog = new AuditLog();
        auditLog.setRequestMethod(request.getMethod());
        auditLog.setRequestPath(request.getRequestURI());
        UserDo user = (UserDo) request.getAttribute("user");
        if (null != user) {
            auditLog.setUserName(user.getName());
        } else {
            auditLog.setUserName("匿名登录");
        }
        auditLogRepository.save(auditLog);
        request.setAttribute("auditLogId", auditLog.getId());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
        Long auditLogId = (Long) request.getAttribute("auditLogId");
        if (null != auditLogId) {
            Optional<AuditLog> optional = auditLogRepository.findById(auditLogId);
            if (optional.isPresent()) {
                AuditLog auditLog = optional.get();
                auditLog.setStatus(response.getStatus());
                auditLogRepository.save(auditLog);
            }
        }
    }
}
