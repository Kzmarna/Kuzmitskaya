package com.coursework.model;

/**
 * Class-entity for student
 * @author Kuzmitskaya Maryia
 * @version 1.0
 */
public class Student {
    private int id;
    private int groupId;
    private String studentName;
    private String studentLastname;
    private String studentPatronymic;

    /**
     * Getter for id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for group id
     * @return group id
     */
    public int getGroupId() {
        return groupId;
    }

    /**
     * Setter for group id
     * @param group_id
     */
    public void setGroupId(int group_id) {
        this.groupId = group_id;
    }

    /**
     * Getter for student name
     * @return student name
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * Setter for student name
     * @param student_name
     */
    public void setStudentName(String student_name) {
        this.studentName = student_name;
    }

    /**
     * Getter for student lastname
     * @return lastname
     */
    public String getStudentLastname() {
        return studentLastname;
    }

    /**
     * Setter for student lastname
     * @param student_lastname
     */
    public void setStudentLastname(String student_lastname) {
        this.studentLastname = student_lastname;
    }

    /**
     * Getter for student patronymic
     * @return patronymic
     */
    public String getStudentPatronymic() {
        return studentPatronymic;
    }

    /**
     * Setter for student patronymic
     * @param student_patronymic
     */
    public void setStudentPatronymic(String student_patronymic) {
        this.studentPatronymic = student_patronymic;
    }

    /**
     * Getter for student full name
     * @return full name
     */
    public String getFullName() {
        return this.studentLastname + " " + this.studentName + " " + this.studentPatronymic;
    }
}
