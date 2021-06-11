import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
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
    @Test
    public void givenDateAsInput_WhenExecuted_ShouldReturnNumberOfEmployees() {
        EmployeePayrollService employeePayrollService=new EmployeePayrollService();
        LocalDate startDate= LocalDate.of(2021, 01, 01);
        LocalDate endDate= LocalDate.now();
        List<EmployeePayrollData> employeePayrollData =
                employeePayrollService.readEmployeePayrollDataForDateRange(IOService.DB_IO, startDate,endDate);
        Assert.assertEquals(3,employeePayrollData.size());



    }
}

