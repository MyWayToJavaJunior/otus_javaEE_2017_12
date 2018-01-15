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

    static final String INSERT_INTO_SQL = "INSERT INTO %s (%s) VALUES (%s)";
    static final String INSERT_OR_UPDATESQL = INSERT_INTO_SQL + " ON DUPLICATE KEY UPDATE %s";



    static final String TBL_ACCOUNTS_ROLES = "AccountsRoles";
    static final String TBL_ACCOUNTS = "Account";
    static final String TBL_DEPARTMENTS = "Departments";
    static final String TBL_LOCATIONS = "Locations";
    static final String TBL_POSITIONS = "Positions";
    static final String TBL_EMPLOYEES = "Employees";
    static final String TBL_EMPLOYEES_CORPORATE_INFO = "EmployeesCorporateInfo";
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

    static final String F_EMPLOYEE_ID = "EmployeeID";
    static final String F_ACCOUNT_ID = "AccountID";
    static final String F_DEPARTMENT_ID = "DepartmentID";
    static final String F_POSITION_ID = "PositionID";
    static final String F_INTERNAL_PHONE_NUMBER = "InternalPhoneNumber";

    static final String F_SALARY = "Salary";


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
