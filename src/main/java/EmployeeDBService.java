import java.sql.*;
import java.lang.IllegalStateException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class EmployeeDBService {
    public Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service?useSS1=false";
        String userName = "root";
        String password = "Ruchi-789";
        Connection connection;
        connection = DriverManager.getConnection(jdbcURL, userName, password);
        return connection;
    }
    public List<EmployeePayrollData>readData() throws EmployeePayrollException{
        String sql = "Select * from employee_payroll;";
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = this.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double Salary = resultSet.getDouble("Salary");
                LocalDate Start = resultSet.getDate("Start").toLocalDate();
                employeePayrollList.add(new EmployeePayrollData(id,name,Salary,Start));

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  employeePayrollList;
    }
    public int updateEmployeeDataUsingStatement(String name, double salary) throws EmployeePayrollException {
        String sql = String.format("UPDATE employee_payroll SET salary=%.2f WHERE name='%s';", salary, name);
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);

        } catch (SQLException e) {
            throw new EmployeePayrollException(
                    EmployeePayrollException.EmployeePayrollExceptionType.UPDATION_ISSUE,
                    "Unable To update data in database");
        }
    }

    public List<EmployeePayrollData> getEmployeePayrollDataFromDB(String name) throws EmployeePayrollException {
        String sql = String.format("SELECT * FROM employee_payroll WHERE name='%s';", name);
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String Employeename = resultSet.getString("name");
                double salary = resultSet.getDouble("Salary");
                LocalDate start = resultSet.getDate("Start").toLocalDate();
                employeePayrollList.add(new EmployeePayrollData(id, Employeename, salary, start));
            }
            return employeePayrollList;
        } catch (SQLException e) {
            throw new EmployeePayrollException(
                    EmployeePayrollException.EmployeePayrollExceptionType.UPDATION_ISSUE,
                    "Unable to get data from database");
        }
    }

    public int update1EmployeeDataUsingStatement(String name, double salary) throws EmployeePayrollException {
        return this.updateEmployeeDataUsingStatement(name, salary);
    }
}