package io;

import model.DataAnalyst;
import model.Employee;
import model.SecuritySpecialist;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
    public static void saveEmployee(Employee employee, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(String.valueOf(employee.getId()));
            writer.newLine();
            writer.write(employee.getName());
            writer.newLine();
            writer.write(employee.getSurname());
            writer.newLine();
            writer.write(String.valueOf(employee.getBirthYear()));
            writer.newLine();

            if (employee instanceof DataAnalyst) {
                writer.write("DataAnalyst");
            } else if (employee instanceof SecuritySpecialist) {
                writer.write("SecuritySpecialist");
            } else {
                writer.write("Unknown");
            }
            writer.newLine();

            System.out.println("FileManager: Employee saved to file: " + filename);
        } catch (IOException e) {
            System.out.println("FileManager ERROR: Could not save employee to file");
            System.out.println("  Reason: " + e.getMessage());
        }
    }

    public static Employee loadEmployee(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String idLine = reader.readLine();
            String name = reader.readLine();
            String surname = reader.readLine();
            String birthYearLine = reader.readLine();
            String type = reader.readLine();

            if (idLine == null || name == null || surname == null || birthYearLine == null || type == null) {
                System.out.println("FileManager ERROR: Invalid employee file format");
                return null;
            }

            int id = Integer.parseInt(idLine.trim());
            int birthYear = Integer.parseInt(birthYearLine.trim());

            if (type.equalsIgnoreCase("DataAnalyst")) {
                System.out.println("FileManager: Employee loaded from file: " + filename);
                return new DataAnalyst(id, name, surname, birthYear);
            } else if (type.equalsIgnoreCase("SecuritySpecialist")) {
                System.out.println("FileManager: Employee loaded from file: " + filename);
                return new SecuritySpecialist(id, name, surname, birthYear);
            } else {
                System.out.println("FileManager ERROR: Unknown employee type in file");
                return null;
            }
        } catch (IOException e) {
            System.out.println("FileManager ERROR: Could not load employee from file");
            System.out.println("  Reason: " + e.getMessage());
            return null;
        } catch (NumberFormatException e) {
            System.out.println("FileManager ERROR: Invalid number format in employee file");
            System.out.println("  Reason: " + e.getMessage());
            return null;
        }
    }
}
