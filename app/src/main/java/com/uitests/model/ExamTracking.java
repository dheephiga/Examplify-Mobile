package com.uitests.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class ExamTracking {

    private String answerScriptId;
    private int rollNumber; // Change student to int type to store roll number
    private String subjectCode; // Change subject to String type to store subject code
    private LocalDate examDate;
    private LocalTime attendanceTime;

    public ExamTracking(String answerScriptId, int rollNumber, String subjectCode) {
        this.answerScriptId = answerScriptId;
        this.rollNumber = rollNumber;
        this.subjectCode = subjectCode;
        this.examDate = LocalDate.now();
       this.attendanceTime = LocalTime.now();
    }

    // Getters and setters

    public String getAnswerScriptId() {
        return answerScriptId;
    }

    public void setAnswerScriptId(String answerScriptId) {
        this.answerScriptId = answerScriptId;
    }

    public int getrollNumber() {
        return rollNumber;
    }

    public void setrollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public LocalDate getExamDate() { return examDate;    }

   public void setExamDate(LocalDate examDate) {
        this.examDate = examDate;
    }

    public LocalTime getAttendanceTime() {
       return attendanceTime;
    }

  public void setAttendanceTime(LocalTime attendanceTime) {
        this.attendanceTime = attendanceTime;
    }

}
