/*
-----------------------------------------------------------
📘 Problem: Student Management System (Data Structure - Problem 01)
-----------------------------------------------------------

🔹 Objective:
    - Efficient data storage and searching using structure, array, and functions
    - Implement sorting and searching algorithms in Java

🔹 Task Description:
    1. Create a menu-based student management system.
    2. Allow the user to input student information (name, roll, marks).
       - The user can input as many students as they want.
       - When the user presses ENTER (blank name), input ends automatically.
    3. After all inputs are given, automatically sort the students
       by their marks in descending order (highest → lowest).
    4. Allow searching for a student by:
       - Name, OR
       - Roll, OR
       - Marks
       If found, display that student’s complete information.
    5. The main menu should always remain active until the user chooses to exit.

🔹 Technical Requirements:
    - Use class (structure) for each student
    - Use array or dynamic array (ArrayList)
    - Use user-defined functions/methods
    - Implement a sorting algorithm (Bubble Sort or similar)
    - Implement a searching mechanism (name/roll/marks)
-----------------------------------------------------------
*/


import java.util.ArrayList;
import java.util.Scanner;

// Structure / Encapsulation - Student class
class Student {
    String name;
    int roll;
    int marks;

    Student(String name, int roll, int marks) {
        this.name = name;
        this.roll = roll;
        this.marks = marks;
    }

    void display() {
        System.out.println("Name: " + name + ", Roll: " + roll + ", Marks: " + marks);
    }
}

// Main system that holds students and features
public class StudentSystem {

    // List to store all students (dynamic array)
    static ArrayList<Student> students = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1. Add students (then auto-sort by marks)");
            System.out.println("2. Search student (by name / roll / marks)");
            System.out.println("3. Show all students (sorted by marks)");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            String chStr = sc.nextLine().trim();
            if (chStr.isEmpty()) continue;   // avoid empty
            int choice = Integer.parseInt(chStr);

            switch (choice) {
                case 1:
                    addStudents(sc);     // feature 1
                    break;
                case 2:
                    searchStudents(sc);  // feature 2
                    break;
                case 3:
                    showAll();           // extra
                    break;
                case 4:
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    // ===== Feature 1: Add many students, then sort by marks (high -> low) =====
    static void addStudents(Scanner sc) {
        System.out.println("\n-- Add Students --");
        System.out.println("Type student info. To stop, just press ENTER on name.");

        while (true) {
            System.out.print("\nEnter student name (blank to stop): ");
            String name = sc.nextLine().trim();

            // if user presses just ENTER: stop taking input
            if (name.isEmpty()) {
                break;
            }

            System.out.print("Enter roll: ");
            int roll = Integer.parseInt(sc.nextLine().trim());

            System.out.print("Enter marks: ");
            int marks = Integer.parseInt(sc.nextLine().trim());

            Student s = new Student(name, roll, marks);
            students.add(s);
            System.out.println("Student added.");
        }

        if (students.isEmpty()) {
            System.out.println("No students in the list!");
            return;
        }

        // auto sort after input finished
        sortByMarksDesc();
        System.out.println("\nStudents sorted by marks (High -> Low) after input.");
        showAll();
    }

    // Bubble sort by marks (descending order)
    static void sortByMarksDesc() {
        int n = students.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (students.get(j).marks < students.get(j + 1).marks) {
                    // swap
                    Student temp = students.get(j);
                    students.set(j, students.get(j + 1));
                    students.set(j + 1, temp);
                }
            }
        }
    }

    // Show all students (current sorted list)
    static void showAll() {
        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }
        System.out.println("\n-- Student List (Sorted by Marks High -> Low) --");
        for (Student s : students) {
            s.display();
        }
    }

    // ===== Feature 2: Search by name OR roll OR marks =====
    static void searchStudents(Scanner sc) {
        if (students.isEmpty()) {
            System.out.println("No students to search. Please add students first.");
            return;
        }

        System.out.println("\n-- Search Student --");
        System.out.println("You can type name OR roll OR marks.");
        System.out.print("Enter search key: ");
        String key = sc.nextLine().trim();

        if (key.isEmpty()) {
            System.out.println("Empty input! Try again.");
            return;
        }

        boolean foundAny = false;

        for (Student s : students) {
            boolean match = false;

            // check name (case-insensitive)
            if (s.name.equalsIgnoreCase(key)) {
                match = true;
            }

            // check roll
            try {
                int rollKey = Integer.parseInt(key);
                if (s.roll == rollKey) {
                    match = true;
                }
            } catch (NumberFormatException e) {
                // not an int, ignore
            }

            // check marks
            try {
                int marksKey = Integer.parseInt(key);
                if (s.marks == marksKey) {
                    match = true;
                }
            } catch (NumberFormatException e) {
                // not an int, ignore
            }

            if (match) {
                if (!foundAny) {
                    System.out.println("\nMatched Students:");
                }
                s.display();
                foundAny = true;
            }
        }

        if (!foundAny) {
            System.out.println("No student matched with \"" + key + "\".");
        }
    }
}
