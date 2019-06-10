package com.slz.demo.filter;


import com.slz.demo.enums.OperationEnum;
import com.slz.demo.enums.ReleasedPathEnum;
import com.slz.demo.pojo.User;
import com.slz.demo.utils.Tools;
import com.sun.org.apache.regexp.internal.recompile;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/")
public class MyFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse)servletResponse;
        HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper((HttpServletResponse) response);

        User user=(User)request.getSession().getAttribute("user");
        if(user!=null){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }

        String path=request.getRequestURI();
        System.out.println(path+"\t拦截判定.....");
        String pathPrefix=path.split("/",3)[1];
        if(isAjax(servletRequest)){
            System.out.println(path+"\t判定通过");
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }

        try{
            ReleasedPathEnum.valueOf(pathPrefix);
            System.out.println(path+"\t判定通过");
            filterChain.doFilter(servletRequest,servletResponse);
        }catch (IllegalArgumentException e){
            if(pathPrefix.endsWith(".ico") || pathPrefix.startsWith("static")){
                System.out.println(path+"\t判定通过");
                filterChain.doFilter(servletRequest,servletResponse);
            }else{
                System.out.println(request.getRequestURL()+"\t判定失败");
                response.sendRedirect("loginEnter");
            }

        }


//        System.out.println(request.getRequestURL()+"\t拦截判定.....");
//        if(isReleasedPath(request)){
//            System.out.println(request.getRequestURL()+"\t判定通过");
//            filterChain.doFilter(servletRequest,servletResponse);
//        }else {
//            System.out.println(request.getRequestURL()+"\t判定失败");
//            response.sendRedirect("loginEnter");
//        }
    }

    /**
     * 是否是Ajax请求
     * @param request
     * @return
     */
    private boolean isAjax(ServletRequest request){
        String header = ((HttpServletRequest) request).getHeader("X-Requested-With");
        if("XMLHttpRequest".equalsIgnoreCase(header)){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


    private boolean isReleasedPath(HttpServletRequest request){
        for(ReleasedPathEnum releasedPath:ReleasedPathEnum.values()){
            if(request.getRequestURI().startsWith(releasedPath.getMessage())){
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {

    }
}
