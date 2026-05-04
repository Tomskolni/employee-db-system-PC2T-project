import io.FileManager;
import model.DataAnalyst;
import model.Employee;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== FileManager Round-Trip Test ===\n");

        DataAnalyst grace = new DataAnalyst(99, "Grace", "Hopper", 1906);
        String filename = "test_employee.txt";

        System.out.println("Step 1: Saving employee to file...");
        FileManager.saveEmployee(grace, filename);
        System.out.println();

        System.out.println("Step 2: Loading employee from file...");
        Employee loadedEmployee = FileManager.loadEmployee(filename);
        System.out.println();

        System.out.println("Step 3: Loaded employee data:");
        if (loadedEmployee != null) {
            System.out.println("Name: " + loadedEmployee.getName());
            System.out.println("Surname: " + loadedEmployee.getSurname());
            System.out.println("ID: " + loadedEmployee.getId());
            System.out.println("Class Name: " + loadedEmployee.getClass().getSimpleName());
        } else {
            System.out.println("Load failed.");
        }
    }
}