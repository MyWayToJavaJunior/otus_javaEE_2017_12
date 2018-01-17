package ru.otus.homework02.servlets;

import ru.otus.homework02.storage.interfaces.IDataStorageService;
import ru.otus.homework02.storage.db.MySQLDataStorageService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        IDataStorageService service = new MySQLDataStorageService();
        servletContextEvent.getServletContext().setAttribute(ServletConsts.ATTR_DATA_STORAGE_SERVICE, service);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
