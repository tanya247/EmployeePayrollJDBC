import java.sql.*;
import java.lang.IllegalStateException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class EmployeeDBService {
    private PreparedStatement employeePayrollDataStatement;



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
    public List<EmployeePayrollData> getEmployeePayrollData(String name) {
        List<EmployeePayrollData> employeePayRolls=null;
        if(this.employeePayrollDataStatement ==null)
            this.prepareStatementForEmployeeData();
        try {
            employeePayrollDataStatement.setString(1, name);
            ResultSet resultSet= employeePayrollDataStatement.executeQuery();
            employeePayRolls=this.getEmployeePayrollData(resultSet);
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return employeePayRolls;
    }

    private List<EmployeePayrollData> getEmployeePayrollData(ResultSet resultSet) {
        List<EmployeePayrollData> employeePayRolls=new ArrayList<>();
        try {
            while(resultSet.next()) {
                int id= resultSet.getInt("id");
                String name=resultSet.getString("name");
                double salary=resultSet.getDouble("salary");
                LocalDate startDate=resultSet.getDate("startDate").toLocalDate();
                employeePayRolls.add(new EmployeePayrollData(id, name, salary,startDate));

            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        // TODO Auto-generated method stub
        return employeePayRolls;
    }

    private void prepareStatementForEmployeeData() {
        try {
            Connection connection=this.getConnection();
            String sql = "SELECT * from employee_payroll WHERE name = ?";
            employeePayrollDataStatement = connection.prepareStatement(sql);

        }catch(SQLException e) {
            e.printStackTrace();
        }
    }
    public List<EmployeePayrollData> getRecordsAddedInGivenDateRange(LocalDate startDate, LocalDate endDate) {
        String sql=String.format("Select * from employee_payroll where startdate between '%s' and '%s';", Date.valueOf(startDate),Date.valueOf(endDate));
        return this.getEmployeePayrollData(sql);
    }
    public Map<String, Double> getAverageSalaryByGender() {
        String sql="select gender, AVG(salary) as avg_salary from employee_payroll group by gender;";
        Map<String, Double> genderToAverageSalaryMap = new HashMap<>();
        try (Connection connection=this.getConnection()){

            Statement statement=connection.createStatement();
            ResultSet resultset=statement.executeQuery(sql);
            while(resultset.next()) {
                String gender = resultset.getString("gender");
                double salary = resultset.getDouble("avg_salary");
                genderToAverageSalaryMap.put(gender, salary);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return genderToAverageSalaryMap;
    }
    public EmployeePayrollData addEmployeeToPayRoll(String name, double salary, LocalDate startDate, String gender) {
        int employeeId= -1;
        EmployeePayrollData employeePayRoll=null;
        String sql = String.format("Insert into employee_payroll (name,gender,salary,startDate)" +
                "Values ('%s','%s','%s','%s')",name,gender,salary,Date.valueOf(startDate));
        try (Connection connection=this.getConnection()){
            Statement statement=connection.createStatement();
            int rowAffected= statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
            if(rowAffected ==1) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if(resultSet.next()) employeeId=resultSet.getInt(1);
            }
            employeePayRoll= new EmployeePayrollData(employeeId, name, salary,startDate);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayRoll;
    }
    public EmployeePayRoll addEmployeeToPayRoll(String name, double salary, LocalDate startDate, String gender) {
        int employeeId= -1;
        Connection connection= null;
        EmployeePayRoll employeePayRoll= null;
        try {
            connection=this.getConnection();
            connection.setAutoCommit(false);
        }catch (SQLException e) {
            e.printStackTrace();
        }

        try (Statement statement=connection.createStatement()){
            String sql = String.format("Insert into employee_payroll (name,gender,salary,startDate)" +
                    "Values ('%s','%s','%s','%s')",name,gender,salary,Date.valueOf(startDate));

            int rowAffected= statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
            if(rowAffected ==1) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if(resultSet.next()) employeeId=resultSet.getInt(1);
            }

        }catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        }
        try (Statement statement=connection.createStatement()){
            double deductions = salary * 0.2;
            double taxablepay = salary - deductions;
            double tax = taxablepay * 0.1;
            double netPay = salary - tax;
            String sql = String.format("Insert into payroll_details "+ "(employee_id, basic_pay, deductions, taxable_pay, tax, net_pay) values"+
                    "(%s, %s, %s, %s, %s, %s)", employeeId, salary,deductions, taxablepay, tax, netPay);
            int rowAffected = statement.executeUpdate(sql);
            if(rowAffected ==1) {
                EmployeePayrollData employeePayRollData = new EmployeePayrollData(employeeId, name, salary, startDate);

            }
        }catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        try {
            connection.commit();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            if(connection !=null) {
                try {
                    connection.close();
                }catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return employeePayRoll;

    }
}