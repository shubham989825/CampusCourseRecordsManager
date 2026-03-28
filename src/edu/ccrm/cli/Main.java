package edu.ccrm.cli;

import edu.ccrm.domain.Semester;
import edu.ccrm.domain.Grade;

public class Main {
    public static void main(String[] args) {
        // Test the enums
        System.out.println("Semester: " + Semester.SPRING);
        System.out.println("Grade A: " + Grade.A);
        System.out.println("Grade for score 85: " + Grade.fromScore(85));
    }
}