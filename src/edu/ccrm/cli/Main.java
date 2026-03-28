package edu.ccrm.cli;

import edu.ccrm.domain.Semester;
import edu.ccrm.domain.Grade;
import edu.ccrm.domain.Student;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== CCRM OOP Inheritance Test ===");
        
        // Test Semester and Grade (previous functionality)
        System.out.println("Semester: " + Semester.SUMMER);
        System.out.println("Grade for 85: " + Grade.fromScore(85));
        
        // Test OOP Inheritance - Create a Student
        System.out.println("\n=== Testing Student Inheritance ===");
        Student student1 = new Student("S001", "2023001", "John Doe", "john.doe@uni.edu");
        
        // Demonstrate polymorphism - Student IS-A Person
        student1.displayProfile(); // Calls overridden method
        
        // Test student functionality
        student1.enrollInCourse("CS101");
        student1.enrollInCourse("MATH201");
        student1.displayEnrolledCourses();
        
        // Demonstrate toString() polymorphism
        System.out.println("\nStudent toString(): " + student1);
        
        System.out.println("\nOOP inheritance working correctly!");
    }
}