package com.cgpacalculator.model;

public class Course {
    private int id;
    private String title;
    private String code;
    private int credit;
    private String letterGrade;
    private double gradePoint;
    private double weightedGradePoint;

    public Course(int id, String title, String code, int credit, String letterGrade) {
        this.id = id;
        this.title = title;
        this.code = code;
        this.credit = credit;
        this.letterGrade = letterGrade;
        this.gradePoint = GradeScale.getGradePoint(letterGrade);
        this.weightedGradePoint = this.credit * this.gradePoint;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
        this.weightedGradePoint = this.credit * this.gradePoint;
    }

    public String getLetterGrade() {
        return letterGrade;
    }

    public void setLetterGrade(String letterGrade) {
        this.letterGrade = letterGrade;
        this.gradePoint = GradeScale.getGradePoint(letterGrade);
        this.weightedGradePoint = this.credit * this.gradePoint;
    }

    public double getGradePoint() {
        return gradePoint;
    }

    public double getWeightedGradePoint() {
        return weightedGradePoint;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", code='" + code + '\'' +
                ", credit=" + credit +
                ", letterGrade='" + letterGrade + '\'' +
                ", gradePoint=" + gradePoint +
                ", weightedGradePoint=" + weightedGradePoint +
                '}';
    }
}
