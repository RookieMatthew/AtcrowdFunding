package com.zsr.interceptor;

import com.zsr.bean.User;
import com.zsr.utils.Const;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;


/**
 * Demo class
 * 请求访问控制的拦截器
 * @author shourenzhang
 * @date 2019/8/5 10:18
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Set<String> url = new HashSet<>();
        url.add("/index.htm");
        url.add("/login.htm");
        url.add("/reg.htm");
        url.add("/doLogout.do");
        url.add("/doLogin.do");
        String servletPath = request.getServletPath();
        if (url.contains(servletPath)){
            return true;
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        if(user!=null){
            return true;
        }else {
            response.sendRedirect(request.getContextPath()+"/login.htm");
            return false;
        }
    }
}
