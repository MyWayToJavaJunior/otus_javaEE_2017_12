package storage.db;

class DBConsts {

    static final String FT_ID = "BIGINT(20) NOT NULL AUTO_INCREMENT";
    static final String FT_BIG_INT = "BIGINT(20) NOT NULL DEFAULT 0";
    static final String FT_INT = "INT(5) NOT NULL DEFAULT 0";
    static final String FT_STRING = "VARCHAR(255) DEFAULT NULL";
    static final String FOREIGN_KEY = "FOREIGN KEY(%s) REFERENCES %s(%s) ON DELETE CASCADE";

    static final String DROP_DATABASE_SQL = "DROP DATABASE IF EXISTS %s";
    static final String CREATE_DATABASE_SQL = "CREATE DATABASE IF NOT EXISTS %s";
    static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS %s(%s, primary key (%s))";
    static final String CREATE_VIEW_SQL = "CREATE OR REPLACE VIEW %s AS %s";

    static final String CREATE_PROC_SQL = "CREATE PROCEDURE %s BEGIN %s END";
    static final String CREATE_FUNCTION_SQL = "CREATE FUNCTION %s RETURNS %s BEGIN %s RETURN %s; END";

    static final String INSERT_INTO_SQL = "INSERT INTO %s (%s) VALUES (%s)";
    static final String INSERT_OR_UPDATE_SQL = INSERT_INTO_SQL + " ON DUPLICATE KEY UPDATE %s";

    static final String TBL_ACCOUNTS_ROLES = "AccountsRoles";
    static final String TBL_ACCOUNTS = "Accounts";
    static final String TBL_DEPARTMENTS = "Departments";
    static final String TBL_LOCATIONS = "Locations";
    static final String TBL_POSITIONS = "Positions";
    static final String TBL_EMPLOYEES_PERSONAL_INFO = "EmployeesPersonalInfo";
    static final String TBL_EMPLOYEES = "Employees";
    static final String TBL_SALARIES = "Salaries";

    static final String F_ID = "ID";
    static final String F_NAME = "Name";

    static final String F_ACCOUNTS_ROLE_ID = "AccountsRoleID";
    static final String F_LOGIN = "Login";
    static final String F_PASSWORD = "Password";

    static final String F_LOCATION_ID = "LocationID";
    static final String F_FIRST_NAME = "FirstName";
    static final String F_MIDDLE_NAME = "MiddleName";
    static final String F_SECOND_NAME = "SecondName";
    static final String F_PERSONAL_EMAIL = "PersonalEmail";

    static final String F_PERSONAL_INFO_ID = "PersonalInfoID";
    static final String F_EMPLOYEE_ID = "EmployeeID";
    static final String F_ACCOUNT_ID = "AccountID";
    static final String F_DEPARTMENT_ID = "DepartmentID";
    static final String F_POSITION_ID = "PositionID";
    static final String F_INTERNAL_PHONE_NUMBER = "InternalPhoneNumber";

    static final String F_SALARY = "Salary";

    static final String VIEW_ACCOUNTS = "V_Accounts";
    static final String VIEW_EMPLOYEES_PERSONAL_INFO = "V_EmployeesPersonalInfo";
    static final String VIEW_EMPLOYEES = "V_Employees";

    static final String F_ROLE_NAME = "RoleName";
    static final String F_LOCATION_NAME = "LocationName";
    static final String F_DEPARTMENT_NAME = "DepartmentName";
    static final String F_POSITION_NAME = "PositionName";
    static final String F_SALARY_ID = "SalaryID";

    static final String SP_DELETE_EMPLOYEE_BY_ID = "SP_DeleteEmployeeByID(%s)";
    static final String FN_GET_MAX_SALARY = "FN_GetMaxSalary()";
    static final String SP_GET_ALL_EMPLOYEES_WITH_MAX_SALARY = "SP_GetAllEmployeesWithMaxSalary()";

    static String newFieldDesc(String fieldName, String fieldType) {
        return String.format("%s %s", fieldName, fieldType);
    }

    static String newIDFieldDesc(String fieldName) {
        return newFieldDesc(fieldName, FT_ID);
    }

    static String newBigIntFieldDesc(String fieldName) {
        return newFieldDesc(fieldName, FT_BIG_INT);
    }

    static String newForeignKey(String fieldName, String referenceTableName, String referenceTableFieldName) {
        return String.format(FOREIGN_KEY, fieldName, referenceTableName, referenceTableFieldName);
    }

    static String newStringFieldDesc(String fieldName) {
        return newFieldDesc(fieldName, FT_STRING);
    }

}
