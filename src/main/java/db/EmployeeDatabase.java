package db;

import model.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    public boolean addEmployee(Employee employee) {
        if (employee == null || employees.containsKey(employee.getId())) {
            return false;
        }

        employees.put(employee.getId(), employee);

        if (employee.getId() >= nextId) {
            nextId = employee.getId() + 1;
        }

        return true;
    }

    public boolean addEmployeeWithId(int id, String name, String surname, int birthYear, String type) {
        if (id <= 0 || employees.containsKey(id)) {
            return false;
        }

        Employee employee;

        if (type.equalsIgnoreCase("DataAnalyst")) {
            employee = new DataAnalyst(id, name, surname, birthYear);
        } else if (type.equalsIgnoreCase("SecuritySpecialist")) {
            employee = new SecuritySpecialist(id, name, surname, birthYear);
        } else {
            return false;
        }

        employees.put(id, employee);

        if (id >= nextId) {
            nextId = id + 1;
        }

        return true;
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

    public List<Employee> getAllSortedBySurname() {
        List<Employee> sorted = new ArrayList<>(employees.values());
        
        Collections.sort(sorted, new Comparator<Employee>() {
            public int compare(Employee e1, Employee e2) {
                return e1.getSurname().compareTo(e2.getSurname());
            }
        });
        
        return sorted;
    }

    public List<Employee> getDataAnalystsSortedBySurname() {
        List<Employee> analysts = getAllDataAnalysts();
        
        Collections.sort(analysts, new Comparator<Employee>() {
            public int compare(Employee e1, Employee e2) {
                return e1.getSurname().compareTo(e2.getSurname());
            }
        });
        
        return analysts;
    }

    public List<Employee> getSecuritySpecialistsSortedBySurname() {
        List<Employee> specialists = getAllSecuritySpecialists();
        
        Collections.sort(specialists, new Comparator<Employee>() {
            public int compare(Employee e1, Employee e2) {
                return e1.getSurname().compareTo(e2.getSurname());
            }
        });
        
        return specialists;
    }

    public Employee getEmployeeWithMostConnections() {
        if (employees.isEmpty()) {
            return null;
        }
        
        Employee mostConnected = null;
        int maxConnections = -1;
        
        for (Employee employee : employees.values()) {
            int connectionCount = employee.getCollaborators().size();
            
            if (connectionCount > maxConnections) {
                maxConnections = connectionCount;
                mostConnected = employee;
            }
        }
        
        return mostConnected;
    }

    public int getCountMostConnections() {
        Employee mostConnected = getEmployeeWithMostConnections();
        
        if (mostConnected == null) {
            return 0;
        }
        
        return mostConnected.getCollaborators().size();
    }

    public CoopLevel getPrevailingCooperationQuality() {
        int poorCount = 0;
        int averageCount = 0;
        int goodCount = 0;
        
        for (Employee employee : employees.values()) {
            for (Collaborator collab : employee.getCollaborators()) {
                if (collab.getLevel() == CoopLevel.POOR) {
                    poorCount++;
                } else if (collab.getLevel() == CoopLevel.AVERAGE) {
                    averageCount++;
                } else if (collab.getLevel() == CoopLevel.GOOD) {
                    goodCount++;
                }
            }
        }
        
        if (poorCount == 0 && averageCount == 0 && goodCount == 0) {
            return null;
        }
        
        if (goodCount >= averageCount && goodCount >= poorCount) {
            return CoopLevel.GOOD;
        } else if (averageCount >= poorCount) {
            return CoopLevel.AVERAGE;
        } else {
            return CoopLevel.POOR;
        }
    }
}
