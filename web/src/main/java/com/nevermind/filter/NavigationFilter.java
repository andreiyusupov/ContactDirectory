package com.nevermind.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class NavigationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig){

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

       String requestURI=((HttpServletRequest)request).getRequestURI();
        System.out.println(requestURI);

        if(!requestURI.equals("/")&&!requestURI.startsWith("/api/contacts")
                &&!requestURI.contains("/scripts/")){
            System.out.println("REDIRECT");
            request.getServletContext()
                    .getRequestDispatcher("/")
                    .forward(request, response);
        }else {
            System.out.println("CHAIN");
            chain.doFilter(request,response);
        }
    }

    @Override
    public void destroy() {

    }
}
