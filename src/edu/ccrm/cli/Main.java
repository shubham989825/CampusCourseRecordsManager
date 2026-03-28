package edu.ccrm.cli;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.*;
import edu.ccrm.service.StudentService;
import edu.ccrm.service.CourseService;
import edu.ccrm.io.ImportExportService;
import edu.ccrm.io.BackupService;
import edu.ccrm.exceptions.DuplicateEnrollmentException;
import edu.ccrm.exceptions.MaxCreditLimitExceededException;

import java.nio.file.Path;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== CCRM File I/O with NIO.2 Test ===");
        
        // Singleton configuration
        AppConfig config = AppConfig.getInstance();
        
        // Initialize services
        StudentService studentService = new StudentService();
        CourseService courseService = new CourseService();
        ImportExportService importExportService = new ImportExportService(studentService, courseService);
        BackupService backupService = new BackupService(importExportService);
        
        try {
            // Test CSV import using NIO.2
            System.out.println("\n=== Testing CSV Import ===");
            Path studentsFile = config.getStudentDataFile();
            Path coursesFile = config.getCourseDataFile();
            
            int studentsImported = importExportService.importStudentsFromCSV(studentsFile);
            int coursesImported = importExportService.importCoursesFromCSV(coursesFile);
            
            System.out.println("Imported: " + studentsImported + " students, " + coursesImported + " courses");
            
            // Test enrollment with imported data
            System.out.println("\n=== Testing Enrollment with Imported Data ===");
            Student john = studentService.findStudentById("S001").orElseThrow();
            Course cs101 = courseService.getCourseByCode("CS101").orElseThrow();
            Course math201 = courseService.getCourseByCode("MATH201").orElseThrow();
            
            try {
                studentService.enrollStudentInCourse(john, cs101);
                studentService.enrollStudentInCourse(john, math201);
                
                // Record grades
                john.getEnrollments().get(0).recordMarks(85.0);
                john.getEnrollments().get(1).recordMarks(92.0);
                
            } catch (DuplicateEnrollmentException | MaxCreditLimitExceededException e) {
                System.err.println("Enrollment error: " + e.getMessage());
            }
            
            // Test CSV export
            System.out.println("\n=== Testing CSV Export ===");
            Path exportDir = config.getDataDirectory().resolve("export");
            java.nio.file.Files.createDirectories(exportDir);
            
            importExportService.exportStudentsToCSV(exportDir.resolve("students_export.csv"));
            importExportService.exportCoursesToCSV(exportDir.resolve("courses_export.csv"));
            importExportService.generateStudentReport(exportDir.resolve("report.txt"));
            
            // Test backup service with recursion
            System.out.println("\n=== Testing Backup Service ===");
            Path backupDir = backupService.createBackup();
            
            // Test recursive directory size calculation
            System.out.println("\n=== Testing Recursive Directory Size ===");
            long backupSize = backupService.calculateDirectorySize(backupDir);
            System.out.println("Backup size: " + backupSize + " bytes");
            
            // Test recursive file listing
            System.out.println("\n=== Testing Recursive File Listing (Depth 2) ===");
            backupService.listFilesByDepth(backupDir, 0, 2);
            
            // Test backup analysis
            System.out.println("\n=== Testing Backup Analysis ===");
            backupService.analyzeBackups();
            
        } catch (IOException e) {
            System.err.println("File I/O error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\nFile I/O with NIO.2 working correctly!");
    }
}