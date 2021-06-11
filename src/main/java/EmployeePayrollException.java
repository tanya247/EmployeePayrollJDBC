@SuppressWarnings("serial")
public class EmployeePayrollException extends Exception {

    public enum EmployeePayrollExceptionType {
        EMPLOYEE_DATA_RETRIEVE_ISSUE, UPDATION_ISSUE;
    }

    public EmployeePayrollExceptionType type;

    public EmployeePayrollException() {

    }

    public EmployeePayrollException(EmployeePayrollExceptionType type, String message) {
        super(message);
        this.type = type;
    }
}