package com.heshi.acl.filters;

import com.heshi.audit.entity.UserDo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AclInteceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String method = request.getMethod();
        UserDo userDo = (UserDo) request.getAttribute("user");
        boolean result = false;
        if (null == userDo) {
            response.getWriter().write("no permission");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        } else {
            if ("get".equalsIgnoreCase(method)) {
                result = "r".equalsIgnoreCase(userDo.getPermission()) || "w".equalsIgnoreCase(userDo.getPermission());
            } else {
                result = "w".equalsIgnoreCase(userDo.getPermission());
            }
        }
        return result;
    }
}
