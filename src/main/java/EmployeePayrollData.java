import java.time.LocalDate;
import java.util.Objects;

public class EmployeePayrollData {
    public int id;
    public double Salary;
    public LocalDate Start;
    public String name;

    public EmployeePayrollData(int id, String name, double Salary, LocalDate Start) {
        super();
        this.id = id;
        this.name = name;
        this.Salary = Salary;
        this.Start = Start;

    }
    public void setId(int id) {
        this.id = id;
    }

    public void setSalary(double salary) {
        Salary = salary;
    }

    public void setStart(LocalDate start) {
        Start = start;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public double getSalary() {
        return Salary;
    }

    public LocalDate getStart() {
        return Start;
    }

    public String getName() {
        return name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeePayrollData that = (EmployeePayrollData) o;
        return id == that.id && Double.compare(that.Salary, Salary) == 0 && Objects.equals(Start, that.Start) && Objects.equals(name, that.name);
    }


}
