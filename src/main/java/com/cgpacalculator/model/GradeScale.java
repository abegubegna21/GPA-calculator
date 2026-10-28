package com.cgpacalculator.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


public class GradeScale {

    private static final Map<String, Double> GRADE_POINTS = new LinkedHashMap<>();

    static {
        GRADE_POINTS.put("A+", 4.00);
        GRADE_POINTS.put("A", 4.00);
        GRADE_POINTS.put("A-", 3.75);
        GRADE_POINTS.put("B+", 3.50);
        GRADE_POINTS.put("B", 3.00);
        GRADE_POINTS.put("B-", 2.75);
        GRADE_POINTS.put("C+", 2.50);
        GRADE_POINTS.put("C", 2.00);
        GRADE_POINTS.put("C-", 1.50);
        GRADE_POINTS.put("D", 1.00);
        GRADE_POINTS.put("F", 0.00);
    }

    
    public static double getGradePoint(String letterGrade) {
        if (letterGrade == null) {
            return 0.0;
        }
        return GRADE_POINTS.getOrDefault(letterGrade.trim().toUpperCase(), 0.0);
    }

   
    public static Set<String> getAvailableGrades() {
        return GRADE_POINTS.keySet();
    }
}
