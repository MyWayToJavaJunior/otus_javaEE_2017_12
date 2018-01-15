package entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private long id;
    private String login;
    private String password;
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
	importData
	getEmployee
	getAllEmployees

DBService
	MySQLConnectionFactory connectionFactory;

	CatalogsDAO catalogsDAO
    EmployeeDAO employeeDAO

    createDataBase

	saveEmployee
	importData

	getEmployee
	getAllEmployees

RequestHandler
    DBService dBService
	HandleRequest(HTTPRequest request, HTTPResponse response)


 */