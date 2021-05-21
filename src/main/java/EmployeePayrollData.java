import java.time.LocalDate;

public class EmployeePayrollData {
    private final int id;
    private final double Salary;
    private final LocalDate Start;
    private final String name;

    public EmployeePayrollData(int id, String name, double Salary, LocalDate Start) {
        super();
        this.id = id;
        this.name = name;
        this.Salary = Salary;
        this.Start = Start;
    }

    @Override
    public String toString() {
        return "EmployeePayrollData{" +
                "id=" + id +
                ", Salary=" + Salary +
                ", Start=" + Start +
                ", name='" + name + '\'' +
                '}';
    }
}
