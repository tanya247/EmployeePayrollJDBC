import java.util.List;

public class EmployeePayrollService {
    EmployeeDBService employeeDBService = new EmployeeDBService();
    public List<EmployeePayrollData> readData() throws EmployeePayrollException{
        return employeeDBService.readData();
    }
}
