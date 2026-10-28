package com.cgpacalculator.service;

import java.util.List;

import com.cgpacalculator.model.Course;

public class CGPACalculator {

    
    public static int calculateTotalCredits(List<Course> courses) {
        if (courses == null || courses.isEmpty()) {
            return 0;
        }
        int totalCredits = 0;
        for (Course course : courses) {
            totalCredits += course.getCredit();
        }
        return totalCredits;
    }

    
    public static double calculateTotalGradePoints(List<Course> courses) {
        if (courses == null || courses.isEmpty()) {
            return 0.0;
        }
        double totalPoints = 0.0;
        for (Course course : courses) {
            totalPoints += course.getWeightedGradePoint();
        }
        return totalPoints;
    }

    
    public static double calculateCGPA(List<Course> courses) {
        int totalCredits = calculateTotalCredits(courses);
        if (totalCredits == 0) {
            return 0.0;
        }
        double totalGradePoints = calculateTotalGradePoints(courses);
        return totalGradePoints / totalCredits;
    }

       
    public static String determineAcademicStatus(double cgpa) {
        if (cgpa >= 2.00) {
            return "Pass";
        } else if (cgpa >= 1.75) {
            return "Warning";
        } else if (cgpa >= 1.50) {
            return "Readmission";
        } else {
            return "Academic Dismissal";
        }
    }
}
