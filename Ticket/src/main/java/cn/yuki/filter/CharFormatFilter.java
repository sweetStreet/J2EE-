package cn.yuki.filter;

import org.springframework.http.HttpRequest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CharFormatFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if(servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse){
            HttpServletRequest httpReq = (HttpServletRequest) servletRequest;
            HttpServletResponse httpRes = (HttpServletResponse) servletResponse;
            httpReq.setCharacterEncoding("utf-8");
            httpRes.setContentType("text/html; charset=utf-8");
//            httpRes.setHeader("Pragma","No-cache");
//            httpRes.setHeader("Cache-Control","no-cache");
//            httpRes.setDateHeader("Expires", 0);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {

    }
}
