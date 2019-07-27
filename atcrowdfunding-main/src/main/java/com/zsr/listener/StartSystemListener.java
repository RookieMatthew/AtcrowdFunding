package com.zsr.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *  用于服务器启动时将项目路径存入域中
 * @author ACER
 * @date 2019/7/26
 */
public class StartSystemListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("APP_PATH",servletContext.getContextPath());
        System.out.println("APP_PATH........");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
