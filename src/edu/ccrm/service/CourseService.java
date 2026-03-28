package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import edu.ccrm.interfaces.Searchable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Demonstrates lambda expressions, Stream API, and search functionality
 */
public class CourseService implements Searchable<Course> {
    private final List<Course> courses;
    
    public CourseService() {
        this.courses = new ArrayList<>();
    }
    
    // Add course
    public void addCourse(Course course) {
        courses.add(course);
        System.out.println("Added course: " + course.getCode() + " - " + course.getTitle());
    }
    
    // Implement Searchable interface using lambda/Stream API
    @Override
    public List<Course> search(String keyword) {
        return courses.stream()
                     .filter(course -> 
                         course.getCode().toLowerCase().contains(keyword.toLowerCase()) ||
                         course.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                         course.getInstructor().toLowerCase().contains(keyword.toLowerCase()) ||
                         course.getDepartment().toLowerCase().contains(keyword.toLowerCase()))
                     .collect(Collectors.toList());
    }
    
    // Search by instructor using Stream API
    public List<Course> searchByInstructor(String instructor) {
        return courses.stream()
                     .filter(course -> course.getInstructor().equalsIgnoreCase(instructor))
                     .collect(Collectors.toList());
    }
    
    // Search by department using Stream API
    public List<Course> searchByDepartment(String department) {
        return courses.stream()
                     .filter(course -> course.getDepartment().equalsIgnoreCase(department))
                     .collect(Collectors.toList());
    }
    
    // Search by semester using Stream API
    public List<Course> searchBySemester(Semester semester) {
        return courses.stream()
                     .filter(course -> course.getSemester() == semester)
                     .collect(Collectors.toList());
    }
    
    // Get all active courses using Stream API
    public List<Course> getActiveCourses() {
        return courses.stream()
                     .filter(Course::canEnroll)
                     .collect(Collectors.toList());
    }
    
    // Get course by code
    public Optional<Course> getCourseByCode(String code) {
        return courses.stream()
                     .filter(course -> course.getCode().equalsIgnoreCase(code))
                     .findFirst();
    }
    
    // Get all courses
    public List<Course> getAllCourses() {
        return new ArrayList<>(courses);
    }
}