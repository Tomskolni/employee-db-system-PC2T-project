package model;

public class Collaborator {
    private Employee employee;
    private CoopLevel level;

    public Collaborator(Employee employee, CoopLevel level) {
        this.employee = employee;
        this.level = level;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public CoopLevel getLevel() {
        return level;
    }

    public void setLevel(CoopLevel level) {
        this.level = level;
    }
}
