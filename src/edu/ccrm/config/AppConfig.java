package edu.ccrm.config;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Singleton pattern for application configuration
 */
public class AppConfig {
    private static AppConfig instance;
    private Path dataDirectory;
    private Path backupDirectory;
    
    // Private constructor for Singleton
    private AppConfig() {
        this.dataDirectory = Paths.get("data");
        this.backupDirectory = Paths.get("backups");
        initializeDirectories();
    }
    
    // Singleton instance getter
    public static AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }
    
    private void initializeDirectories() {
        try {
            if (!java.nio.file.Files.exists(dataDirectory)) {
                java.nio.file.Files.createDirectories(dataDirectory);
            }
            if (!java.nio.file.Files.exists(backupDirectory)) {
                java.nio.file.Files.createDirectories(backupDirectory);
            }
        } catch (Exception e) {
            System.err.println("Error creating directories: " + e.getMessage());
        }
    }
    
    // Getters
    public Path getDataDirectory() { return dataDirectory; }
    public Path getBackupDirectory() { return backupDirectory; }
    
    public Path getStudentDataFile() { return dataDirectory.resolve("students.csv"); }
    public Path getCourseDataFile() { return dataDirectory.resolve("courses.csv"); }
}