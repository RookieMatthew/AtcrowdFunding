package com.zsr.interceptor;

import com.zsr.manager.service.PermissionService;
import com.zsr.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * Demo class
 * 根据权限不同进行访问控制的拦截器
 * @author shourenzhang
 * @date 2019/8/5 11:21
 */
public class PermissionInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    PermissionService permissionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String servletPath = request.getServletPath();
        Set<String> urls = (Set<String>) request.getSession().getAttribute(Const.PERMISSION_URLS);
        Set<String> allPermissionsUrl = (Set<String>) request.getSession().getServletContext().getAttribute(Const.ALL_PERMISSION_URLS);
        if (!allPermissionsUrl.contains(servletPath)){
            return true;
        }
        if (urls.contains(servletPath)){
            return true;
        }else {
            response.sendRedirect(request.getContextPath()+"/login.htm");
            request.getSession().setAttribute("tip","请登录后访问！");
            return false;
        }
    }
}
