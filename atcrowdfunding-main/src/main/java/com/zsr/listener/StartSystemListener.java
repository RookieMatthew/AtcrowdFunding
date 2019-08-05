package com.zsr.listener;

import com.zsr.manager.service.PermissionService;
import com.zsr.utils.Const;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Set;

/**
 *  用于服务器启动时将项目路径存入域中
 * @author ACER
 * @date 2019/7/26
 */
public class StartSystemListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //将项目根路径放在application域中
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("APP_PATH",servletContext.getContextPath());
        //将所有许可的url地址放在application域中
        ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        PermissionService permissionService = ac.getBean(PermissionService.class);
        Set<String> allPermissionsUrl = permissionService.getAllPermissionsUrl();
        servletContext.setAttribute(Const.ALL_PERMISSION_URLS,allPermissionsUrl);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
