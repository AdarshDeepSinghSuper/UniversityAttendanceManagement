package com.example.attendance;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AttendanceManagement {
    private static final String FILE_NAME = "attendance.txt";
    private Map<String, Boolean> attendance;
    private Scanner scanner;

    public AttendanceManagement() {
        attendance = new HashMap<>();
        scanner = new Scanner(System.in);
        loadAttendanceFromFile();
    }

    public void start() {
        while (true) {
            System.out.println("1. Mark Attendance");
            System.out.println("2. View Attendance");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

            switch (choice) {
                case 1:
                    markAttendance();
                    break;
                case 2:
                    viewAttendance();
                    break;
                case 3:
                    saveAttendanceToFile();
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice! Please choose again.");
            }
        }
    }

    private void markAttendance() {
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();
        System.out.print("Is the student present? (y/n): ");
        String isPresent = scanner.nextLine();
        attendance.put(name, isPresent.equalsIgnoreCase("y"));
        System.out.println("Attendance marked for " + name);
    }

    private void viewAttendance() {
        System.out.println("Attendance List:");
        for (Map.Entry<String, Boolean> entry : attendance.entrySet()) {
            String status = entry.getValue() ? "Present" : "Absent";
            System.out.println(entry.getKey() + ": " + status);
        }
    }

    // Save attendance to a file
    private void saveAttendanceToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Map.Entry<String, Boolean> entry : attendance.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
            System.out.println("Attendance saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving attendance: " + e.getMessage());
        }
    }

    // Load attendance from a file
    private void loadAttendanceFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0];
                    boolean isPresent = Boolean.parseBoolean(parts[1]);
                    attendance.put(name, isPresent);
                }
            }
            System.out.println("Attendance loaded successfully!");
        } catch (FileNotFoundException e) {
            System.out.println("Attendance file not found, starting fresh.");
        } catch (IOException e) {
            System.out.println("Error loading attendance: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        AttendanceManagement system = new AttendanceManagement();
        system.start();
    }
}
