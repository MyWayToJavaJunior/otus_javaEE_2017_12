package storage.db;

public class DBConsts {

    public static final String FT_ID = "BIGINT(20) NOT NULL AUTO_INCREMENT";
    public static final String FT_BIG_INT = "BIGINT(20) NOT NULL DEFAULT 0";
    public static final String FT_INT = "INT(5) NOT NULL DEFAULT 0";
    public static final String FT_STRING = "VARCHAR(255) DEFAULT NULL";
    public static final String FOREIGN_KEY = "FOREIGN KEY(%s) REFERENCES %s(%s) ON DELETE CASCADE";

    public static final String DROP_DATABASE_SQL = "DROP DATABASE IF EXISTS %s";
    public static final String CREATE_DATABASE_SQL = "CREATE DATABASE IF NOT EXISTS %s";
    public static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS %s(%s, primary key (%s))";



    public static final String TBL_ACCOUNTS_ROLES = "AccountsRoles";
    public static final String TBL_ACCOUNTS = "Account";
    public static final String TBL_DEPARTMENTS = "Departments";
    public static final String TBL_LOCATIONS = "Locations";
    public static final String TBL_POSITIONS = "Positions";
    public static final String TBL_EMPLOYEES = "Employees";
    public static final String TBL_EMPLOYEES_CORPORATE_INFO = "EmployeesCorporateInfo";
    public static final String TBL_SALARIES = "Salaries";

    public static final String F_ID = "ID";
    public static final String F_NAME = "Name";
    public static final String F_ACCOUNTS_ROLE_ID = "AccountsRoleID";

    public static final String F_LOCATION_ID = "LocationID";
    public static final String F_FIRST_NAME = "FirstName";
    public static final String F_MIDDLE_NAME = "MiddleName";
    public static final String F_SECOND_NAME = "SecondName";
    public static final String F_PERSONAL_EMAIL = "PersonalEmail";

    public static final String F_EMPLOYEE_ID = "EmployeeID";
    public static final String F_ACCOUNT_ID = "AccountID";
    public static final String F_DEPARTMENT_ID = "DepartmentID";
    public static final String F_POSITION_ID = "PositionID";
    public static final String F_INTERNAL_PHONE_NUMBER = "InternalPhoneNumber";

    public static final String F_SALARY = "Salary";


    public static String newFieldDesc(String fieldName, String fieldType) {
        return String.format("%s %s", fieldName, fieldType);
    }

    public static String newIDFieldDesc(String fieldName) {
        return newFieldDesc(fieldName, FT_ID);
    }

    public static String newBigIntFieldDesc(String fieldName) {
        return newFieldDesc(fieldName, FT_BIG_INT);
    }

    public static String newForeignKey(String fieldName, String referenceTableName, String referenceTableFieldName) {
        return String.format(FOREIGN_KEY, fieldName, referenceTableName, referenceTableFieldName);
    }

    public static String newStringFieldDesc(String fieldName) {
        return newFieldDesc(fieldName, FT_STRING);
    }

}
