package com.uitests.model;

import java.util.List;
import java.io.Serializable;

public class Student implements Serializable {

    private Integer rollNumber;
    private String name;
    private String degree;
    private String department;

    private List<Subject> currentCoursesEnrolled;

    public Student(Integer rollNumber, String name, String degree, String department,List<Subject> currentCoursesEnrolled) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.degree = degree;
        this.department = department;
        this.currentCoursesEnrolled=currentCoursesEnrolled;
    }

    public Student() {

    }

    public Integer getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(Integer rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<Subject> getCurrentCoursesEnrolled() {
        return currentCoursesEnrolled;
    }

    public void setCurrentCoursesEnrolled(List<Subject> currentCoursesEnrolled) {
        this.currentCoursesEnrolled = currentCoursesEnrolled;
    }

    public String getSubjectCode() {

        return "BA19702";
    }

    @Override
    public String toString() {
        return "Student{" +
                "rollNumber=" + rollNumber +
                ", name='" + name + '\'' +
                ", degree='" + degree + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}

