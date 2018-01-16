package servlets;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import entities.Employee;
import entities.Salary;
import storage.IDataStorageService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/output_employees_list")
public class OutputEmployeesListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IDataStorageService dataStorageService = (IDataStorageService) getServletContext().getAttribute(ServletConsts.ATTR_DATA_STORAGE_SERVICE);
        try {
            List<Employee> employees = dataStorageService.getAllEmployees();
            Gson gson = new GsonBuilder().setPrettyPrinting().setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getDeclaringClass() == Salary.class && f.getName().equalsIgnoreCase("employee");
                }

                @Override
                public boolean shouldSkipClass(Class<?> aClass) {
                    return false;
                }
            }).create();

            Type lsitType =  new TypeToken<ArrayList<Employee>>(){}.getType();
            String employeesJSON = gson.toJson(employees, lsitType);
            System.out.println(employeesJSON);

            request.setAttribute(ServletConsts.ATTR_RESULT_MESSAGE, "<pre>" + employeesJSON + "</pre>");
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
