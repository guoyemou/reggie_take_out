package com.yezi.Interceptors;

import com.alibaba.fastjson.JSON;
import com.yezi.common.R;
import com.yezi.entity.User;
import com.yezi.utils.BaseContextUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long id = (Long) request.getSession().getAttribute("id");
        Long userId = (Long) request.getSession().getAttribute("userId");
        if(id != null){
            BaseContextUtils.setCurrentId(id);
            return true;
        }
        if(userId != null){
            BaseContextUtils.setCurrentId(userId);
            return true;
        }
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return false;
    }

}
