package com.soldesk.ex01.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

// 시큐리티에서 iframe으로 jsp 소스 받아와서 여는거 허용시켜주는 필터
public class CSPFilter implements Filter {

	@Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Content-Security-Policy", "frame-ancestors 'self' localhost:9090/ex01/");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}

}
