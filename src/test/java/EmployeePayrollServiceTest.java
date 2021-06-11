import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class EmployeePayrollServiceTest {
    @Test
    public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatch_EmployeeCount() throws EmployeePayrollException {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData;
        employeePayrollData = employeePayrollService.readData();
        System.out.println(employeePayrollData);
        Assertions.assertEquals(3, employeePayrollData.size());

    }
    @Test
    public void givenNewSalaryOfEmployee_WhenUpdated_ShouldSyncWithDatabase() throws EmployeePayrollException {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readData();

        employeePayrollService.updateEmployeeSalary("Bill",300000.00);
       // System.out.println(employeePayrollData);
        boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Bill");
        Assertions.assertTrue(result);
    }
}

