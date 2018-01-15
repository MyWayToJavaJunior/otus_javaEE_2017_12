package entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Accounts {
    private long id;
    private String Login;
    private String Password;
    private AccountsRole role;
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

EmployeeDAO
	saveEmployee
	importEmployees
	getEmployee
	getAllEmployees

DBService
	ConnectionFactory connectionFactory;

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