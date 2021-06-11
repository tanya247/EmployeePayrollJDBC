import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class EmployeePayrollService {

    EmployeeDBService employeeDBService = new EmployeeDBService();
    List<EmployeePayrollData> employeePayrollList;

    public List<EmployeePayrollData> readData() throws EmployeePayrollException {
        return this.employeeDBService.readData();
    }

    public void updateEmployeeSalary(String name, double Salary) throws EmployeePayrollException {
        int result = new EmployeeDBService().update1EmployeeDataUsingStatement(name, Salary);
        if (result == 0)
            return;
        EmployeePayrollData employeePayrollData = this.getEmployeePayrollData(name);
        if (employeePayrollData != null)
            employeePayrollData.setSalary(Salary);
    }

    private EmployeePayrollData getEmployeePayrollData(String name) throws EmployeePayrollException {
        employeePayrollList = this.readData();
        return this.employeePayrollList.stream()
                                       .filter(employeePayrollData -> employeePayrollData.getName().equals(name))
                                       .findFirst()
                                       .orElse(null);
    }

    public boolean checkEmployeePayrollInSyncWithDB(String name) throws EmployeePayrollException {
        List<EmployeePayrollData> employeePayrollList = employeeDBService.getEmployeePayrollDataFromDB(name);
        System.out.println(employeePayrollList.get(0));
        System.out.println(getEmployeePayrollData(name));
        return employeePayrollList.get(0).equals(getEmployeePayrollData(name));
    }
    public List<EmployeePayrollData> readEmployeePayrollDataForDateRange(LocalDate startDate,
                                                                         LocalDate endDate){
        EmployeeDBService employeeDBService = new EmployeeDBService();
        List<EmployeePayrollData> employeePayrollData = employeeDBService.getRecordsAddedInGivenDateRange(startDate, endDate);
        return employeePayrollData;
    }
    public Map<String, Double> readAverageSalaryByGender() {

        EmployeeDBService employeeDBService = new EmployeeDBService();
        return employeeDBService.getAverageSalaryByGender();
    }
    public void addEmployeeToPayRoll(String name, double salary, LocalDate startDate, String gender) {
        EmployeeDBService employeeDBService = new EmployeeDBService();
        employeePayrollList.add(employeeDBService.addEmployeeToPayRoll(name,salary,startDate,gender));


    }



}