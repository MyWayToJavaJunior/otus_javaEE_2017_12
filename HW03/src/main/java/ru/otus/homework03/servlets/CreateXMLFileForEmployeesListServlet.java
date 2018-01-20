package ru.otus.homework03.servlets;

import ru.otus.homework03.HomeWork03Helper;
import ru.otus.homework03.entities.Employee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static ru.otus.homework03.servlets.ServletConsts.*;

//@WebServlet("/" + REQUEST_CREATE_XML_FILE_FOR_EMPLOYEES_LIST)
public class CreateXMLFileForEmployeesListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Employee> employees = HomeWork03Helper.initData();
        try {
            if (HomeWork03Helper.saveDataToXMLFile(employees, HomeWork03Helper.FILE_EMPLOYEES_XML)) {
                request.setAttribute(ServletConsts.ATTR_RESULT_MESSAGE, MSG_XML_FILE_SUCCESSFULLY_CREATED);
                request.setAttribute(ServletConsts.ATTR_RESULT_MESSAGE_CLASS, ATTR_GOOD_RESULT_MESSAGE_CLASS);

            }
            else {
                request.setAttribute(ServletConsts.ATTR_RESULT_MESSAGE, MSG_ERROR_DURING_CREATION_XML_FILE);
                request.setAttribute(ServletConsts.ATTR_RESULT_MESSAGE_CLASS, ATTR_BAD_RESULT_MESSAGE_CLASS);
            }
        } catch (Exception e) {
            request.setAttribute(ServletConsts.ATTR_RESULT_MESSAGE, e.getMessage());
            request.setAttribute(ServletConsts.ATTR_RESULT_MESSAGE_CLASS, ATTR_BAD_RESULT_MESSAGE_CLASS);

        }
    }
}
