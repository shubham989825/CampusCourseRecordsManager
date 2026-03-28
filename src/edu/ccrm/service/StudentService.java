package edu.ccrm.service;

import edu.ccrm.domain.Student;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Grade;
import edu.ccrm.exceptions.DuplicateEnrollmentException;
import edu.ccrm.exceptions.MaxCreditLimitExceededException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Demonstrates Stream API, exception handling, and business logic
 */
public class StudentService {
    private final List<Student> students;
    private static final int MAX_CREDITS_PER_SEMESTER = 18;
    
    public StudentService() {
        this.students = new ArrayList<>();
    }
    
    // Add student
    public void addStudent(Student student) {
        students.add(student);
        System.out.println("Added student: " + student.getFullName());
    }
    
    // Find student by ID using Stream API
    public Optional<Student> findStudentById(String id) {
        return students.stream()
                      .filter(s -> s.getId().equals(id))
                      .findFirst();
    }
    
    // Find student by registration number
    public Optional<Student> findStudentByRegNo(String regNo) {
        return students.stream()
                      .filter(s -> s.getRegNo().equals(regNo))
                      .findFirst();
    }
    
    // Enroll student in course with business rules
    public Enrollment enrollStudentInCourse(Student student, Course course) 
            throws DuplicateEnrollmentException, MaxCreditLimitExceededException {
        
        // Check for duplicate enrollment using Stream API
        boolean alreadyEnrolled = student.getEnrollments().stream()
            .anyMatch(e -> e.getCourse().getCode().equals(course.getCode()));
        
        if (alreadyEnrolled) {
            throw new DuplicateEnrollmentException(student.getFullName(), course.getCode());
        }
        
        // Check credit limit
        int currentCredits = student.getTotalCredits();
        int attemptedCredits = currentCredits + course.getCredits();
        
        if (attemptedCredits > MAX_CREDITS_PER_SEMESTER) {
            throw new MaxCreditLimitExceededException(currentCredits, course.getCredits(), MAX_CREDITS_PER_SEMESTER);
        }
        
        // Enroll student
        Enrollment enrollment = student.enrollInCourse(course);
        System.out.println("Successfully enrolled " + student.getFullName() + " in " + course.getCode());
        return enrollment;
    }
    
    // Get all active students using Stream API
    public List<Student> getActiveStudents() {
        return students.stream()
                      .filter(Student::isActive)
                      .collect(Collectors.toList());
    }
    
    // Get students by department using Stream API
    public List<Student> getStudentsByDepartment(String department) {
        return students.stream()
                      .filter(s -> s.getEnrollments().stream()
                                  .anyMatch(e -> e.getCourse().getDepartment().equals(department)))
                      .collect(Collectors.toList());
    }
    
    // Calculate average GPA for a student using Stream API
    public double calculateGPA(Student student) {
        if (student.getEnrollments().isEmpty()) {
            return 0.0;
        }
        
        double totalGradePoints = student.getEnrollments().stream()
            .filter(Enrollment::isGraded)
            .mapToDouble(Enrollment::calculateGradePoints)
            .sum();
            
        int totalCredits = student.getEnrollments().stream()
            .filter(Enrollment::isGraded)
            .mapToInt(e -> e.getCourse().getCredits())
            .sum();
            
        return totalCredits > 0 ? totalGradePoints / totalCredits : 0.0;
    }
    
    // Get top students by GPA using Stream API
    public List<Student> getTopStudents(int count) {
        return students.stream()
                      .filter(s -> !s.getEnrollments().isEmpty())
                      .sorted((s1, s2) -> Double.compare(calculateGPA(s2), calculateGPA(s1)))
                      .limit(count)
                      .collect(Collectors.toList());
    }
    
    // Get all students
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }
}