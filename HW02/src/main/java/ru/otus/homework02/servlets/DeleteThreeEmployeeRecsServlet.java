package ru.otus.homework02.servlets;

import ru.otus.homework02.storage.interfaces.IDataStorageService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete_three_recs")
public class DeleteThreeEmployeeRecsServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IDataStorageService dataStorageService = (IDataStorageService) getServletContext().getAttribute(ServletConsts.ATTR_DATA_STORAGE_SERVICE);
        try {
            dataStorageService.deleteOneEmployee(1);
            dataStorageService.deleteOneEmployee(2);
            dataStorageService.deleteOneEmployee(3);
            request.setAttribute(ServletConsts.ATTR_RESULT_MESSAGE, ServletConsts.MSG_RECS_SUCCESSFULLY_DELETED);
            request.setAttribute(ServletConsts.ATTR_RESULT_MESSAGE_CLASS, ServletConsts.ATTR_GOOD_RESULT_MESSAGE_CLASS);
        } catch (Exception e) {
            request.setAttribute(ServletConsts.ATTR_RESULT_MESSAGE, e.getMessage());
            request.setAttribute(ServletConsts.ATTR_RESULT_MESSAGE_CLASS, ServletConsts.ATTR_BAD_RESULT_MESSAGE_CLASS);
        }
        request.getRequestDispatcher(ServletConsts.JSP_RESULT).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath());
    }
}
