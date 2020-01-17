package com.geotmt.cacheprime.interceper;

import com.geotmt.cacheprime.base.BaseController;
import com.geotmt.cacheprime.base.common.CurrentHolder;
import com.geotmt.cacheprime.base.common.HttpCode;
import com.geotmt.cacheprime.base.common.SystemUser;
import com.geotmt.cacheprime.utils.RequestUtil;
import com.geotmt.cacheprime.utils.ResponseUitl;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandlerLoginIncepter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SystemUser systemUser = CurrentHolder.getSystemUser();
        if(systemUser == null){
            if(RequestUtil.isAjax(request)){
                ResponseUitl.fail(response,HttpCode.UN_LOGIN);
            }else {
                response.sendRedirect("/");
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }


}
