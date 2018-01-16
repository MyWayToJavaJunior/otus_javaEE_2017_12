package servlets;

import storage.IDataStorageService;
import storage.db.MySQLDataStorageService;

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
