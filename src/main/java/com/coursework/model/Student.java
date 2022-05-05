package com.coursework.model;

public class Student {
    private int id;
    private int groupId;
    private String studentName;
    private String studentLastname;
    private String studentPatronymic;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int group_id) {
        this.groupId = group_id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String student_name) {
        this.studentName = student_name;
    }

    public String getStudentLastname() {
        return studentLastname;
    }

    public void setStudentLastname(String student_lastname) {
        this.studentLastname = student_lastname;
    }

    public String getStudentPatronymic() {
        return studentPatronymic;
    }

    public void setStudentPatronymic(String getStudent_patronymic) {
        this.studentPatronymic = getStudent_patronymic;
    }

    public String getFullName() {
        return this.studentLastname + " " + this.studentName + " " + this.studentPatronymic;
    }
}
