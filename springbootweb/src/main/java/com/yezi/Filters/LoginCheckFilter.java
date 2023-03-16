//package com.yezi.Filters;
//
//import com.alibaba.fastjson.JSON;
//import com.yezi.common.R;
//import org.springframework.util.AntPathMatcher;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
//public class LoginCheckFilter implements Filter {
//    //        匹配路径器，支持通配符
//    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
////        获得本次请求路径
//        String requestURI = request.getRequestURI();
////        定义不需要处理的请求路径
//        String[] urls = new String[]{
//               "/employee/login",
//               "/employee/logout",
//               "/backend/**",
//               "/front/**"
//        };
//        if(check(urls,requestURI)){
//            filterChain.doFilter(request,response);
//            return;
//        }
//        if(request.getSession().getAttribute("id") != null){
//            filterChain.doFilter(request,response);
//            return;
//        }
//        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
//        System.out.println("1");
//        return;
//    }
//    public boolean check(String[] urls,String requestURI){
//        for(int i = 0;i <urls.length;i++){
//            boolean match = PATH_MATCHER.match(urls[i], requestURI);
//            if(match){
//                return true;
//            }
//        }
//        return false;
//    }
//}
