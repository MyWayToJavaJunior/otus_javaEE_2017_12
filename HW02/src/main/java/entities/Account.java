package entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Account {
    private long id;
    private String Login;
    private String Password;
    private AccountRole role;
}


/*

CatalogsDAO
    Connection connection

	saveAccountsRole
	saveAccount
	savePosition
	saveDepartment
	saveLocation
	saveSalary

EmployeesDAO
	saveEmployee
	importEmployees
	getEmployee
	getAllEmployees

DBService
	MySQLConnectionFactory connectionFactory;

	CatalogsDAO catalogsDAO
    EmployeeDAO employeeDAO

    createDataBase

	saveEmployee
	importEmployees

	getEmployee
	getAllEmployees

RequestHandler
    DBService dBService
	HandleRequest(HTTPRequest request, HTTPResponse response)


 */