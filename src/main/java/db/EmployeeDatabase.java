package db;

import model.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeDatabase {
    private Map<Integer, Employee> employees;
    private int nextId;

    public EmployeeDatabase() {
        this.employees = new HashMap<>();
        this.nextId = 1;
    }

    public int addEmployee(String name, String surname, int birthYear, String type) {
        Employee employee;
        
        if (type.equalsIgnoreCase("DataAnalyst")) {
            employee = new DataAnalyst(nextId, name, surname, birthYear);
        } else if (type.equalsIgnoreCase("SecuritySpecialist")) {
            employee = new SecuritySpecialist(nextId, name, surname, birthYear);
        } else {
            return -1;
        }
        
        employees.put(nextId, employee);
        int assignedId = nextId;
        nextId++;
        return assignedId;
    }

    public boolean removeEmployee(int id) {
        if (!employees.containsKey(id)) {
            return false;
        }
        
        Employee employeeToRemove = employees.get(id);
        
        for (Employee employee : employees.values()) {
            employee.removeCollaborator(employeeToRemove);
        }
        
        employees.remove(id);
        return true;
    }

    public Employee getEmployee(int id) {
        return employees.get(id);
    }

    public List<Employee> getAll() {
        return new ArrayList<>(employees.values());
    }

    public List<Employee> getAllDataAnalysts() {
        List<Employee> result = new ArrayList<>();
        
        for (Employee employee : employees.values()) {
            if (employee instanceof DataAnalyst) {
                result.add(employee);
            }
        }
        
        return result;
    }

    public List<Employee> getAllSecuritySpecialists() {
        List<Employee> result = new ArrayList<>();
        
        for (Employee employee : employees.values()) {
            if (employee instanceof SecuritySpecialist) {
                result.add(employee);
            }
        }
        
        return result;
    }

    public int countDataAnalysts() {
        int count = 0;
        
        for (Employee employee : employees.values()) {
            if (employee instanceof DataAnalyst) {
                count++;
            }
        }
        
        return count;
    }

    public int countSecuritySpecialists() {
        int count = 0;
        
        for (Employee employee : employees.values()) {
            if (employee instanceof SecuritySpecialist) {
                count++;
            }
        }
        
        return count;
    }

    public boolean addCollaboration(int employeeId, int collaboratorId, CoopLevel level) {
        if (!employees.containsKey(employeeId) || !employees.containsKey(collaboratorId)) {
            return false;
        }
        
        Employee employee = employees.get(employeeId);
        Employee collaborator = employees.get(collaboratorId);
        
        Collaborator newCollaboration = new Collaborator(collaborator, level);
        employee.addCollaborator(newCollaboration);
        
        return true;
    }

    public int getTotalEmployees() {
        return employees.size();
    }

    public boolean employeeExists(int id) {
        return employees.containsKey(id);
    }
}
