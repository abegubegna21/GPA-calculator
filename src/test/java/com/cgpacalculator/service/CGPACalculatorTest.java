package com.cgpacalculator.service;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cgpacalculator.model.Course;
import com.cgpacalculator.model.GradeScale;

public class CGPACalculatorTest {

    private List<Course> sampleCourses;

    @BeforeEach
    void setUp() {
        sampleCourses = new ArrayList<>();
      
        sampleCourses.add(new Course(1, "Object Oriented Programming", "CoSc2022", 5, "A"));
        sampleCourses.add(new Course(2, "Data Structures and Algorithms", "CoSc2021", 4, "B"));
        sampleCourses.add(new Course(3, "Database Systems", "CoSc2031", 5, "C"));
        sampleCourses.add(new Course(4, "Computer Networks", "CoSc2041", 5, "B"));
    }

    @Test
    @DisplayName("Test Letter Grade to Grade Point Mapping")
    void testGradeScaleMapping() {
        assertEquals(4.00, GradeScale.getGradePoint("A"), 0.001);
        assertEquals(3.75, GradeScale.getGradePoint("A-"), 0.001);
        assertEquals(3.50, GradeScale.getGradePoint("B+"), 0.001);
        assertEquals(3.00, GradeScale.getGradePoint("B"), 0.001);
        assertEquals(2.75, GradeScale.getGradePoint("B-"), 0.001);
        assertEquals(2.50, GradeScale.getGradePoint("C+"), 0.001);
        assertEquals(2.00, GradeScale.getGradePoint("C"), 0.001);
        assertEquals(1.50, GradeScale.getGradePoint("C-"), 0.001);
        assertEquals(1.00, GradeScale.getGradePoint("D"), 0.001);
        assertEquals(0.00, GradeScale.getGradePoint("F"), 0.001);
       
        assertEquals(0.00, GradeScale.getGradePoint("XYZ"), 0.001);
    }

    @Test
    @DisplayName("Test Course Weighted Grade Point Calculation")
    void testWeightedGradePoint() {
        Course courseA = new Course(1, "OOP", "CS101", 5, "A");
        assertEquals(20.0, courseA.getWeightedGradePoint(), 0.001);

        Course courseB = new Course(2, "DSA", "CS102", 4, "B");
        assertEquals(12.0, courseB.getWeightedGradePoint(), 0.001);
    }

    @Test
    @DisplayName("Test Sum of All Course Credits (5 + 4 + 5 + 5 = 19)")
    void testCalculateTotalCredits() {
        int totalCredits = CGPACalculator.calculateTotalCredits(sampleCourses);
        assertEquals(19, totalCredits);
    }

    @Test
    @DisplayName("Test Sum of All Weighted Grade Points (20 + 12 + 10 + 15 = 57)")
    void testCalculateTotalGradePoints() {
        double totalPoints = CGPACalculator.calculateTotalGradePoints(sampleCourses);
        assertEquals(57.0, totalPoints, 0.001);
    }

    @Test
    @DisplayName("Test CGPA Calculation (Total Points / Total Credits)")
    void testCalculateCGPA() {
        double cgpa = CGPACalculator.calculateCGPA(sampleCourses);
        // 57.0 / 19 = 3.0
        assertEquals(3.00, cgpa, 0.01);

        // Slide example: 46 / 14 = 3.285...
        List<Course> slideCourses = new ArrayList<>();
        slideCourses.add(new Course(1, "Course 1", "C1", 10, "A")); // 10 * 4.0 = 40
        slideCourses.add(new Course(2, "Course 2", "C2", 4, "C-")); // 4 * 1.5 = 6.0 (total 46)
        double cgpaSlide = CGPACalculator.calculateCGPA(slideCourses);
        assertEquals(46.0 / 14.0, cgpaSlide, 0.01);
    }

    @Test
    @DisplayName("Test Academic Status Determination")
    void testAcademicStatus() {
        // >= 2.0: Pass
        assertEquals("Pass", CGPACalculator.determineAcademicStatus(4.0));
        assertEquals("Pass", CGPACalculator.determineAcademicStatus(2.0));

        // >= 1.75 and < 2.0: Warning
        assertEquals("Warning", CGPACalculator.determineAcademicStatus(1.99));
        assertEquals("Warning", CGPACalculator.determineAcademicStatus(1.75));

        // >= 1.50 and < 1.75: Readmission
        assertEquals("Readmission", CGPACalculator.determineAcademicStatus(1.74));
        assertEquals("Readmission", CGPACalculator.determineAcademicStatus(1.50));

        // < 1.50: Academic Dismissal
        assertEquals("Academic Dismissal", CGPACalculator.determineAcademicStatus(1.49));
        assertEquals("Academic Dismissal", CGPACalculator.determineAcademicStatus(0.50));
    }

    @Test
    @DisplayName("Test Empty Course List Edge Case")
    void testEmptyList() {
        List<Course> empty = new ArrayList<>();
        assertEquals(0, CGPACalculator.calculateTotalCredits(empty));
        assertEquals(0.0, CGPACalculator.calculateTotalGradePoints(empty), 0.001);
        assertEquals(0.0, CGPACalculator.calculateCGPA(empty), 0.001);
        assertEquals("Academic Dismissal", CGPACalculator.determineAcademicStatus(0.0));
    }
}
