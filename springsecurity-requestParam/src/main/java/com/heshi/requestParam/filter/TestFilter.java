package com.heshi.requestParam.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author by fukun
 */
@Component
public class TestFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setAttribute("nameFromFilter", "2019-11-23");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
