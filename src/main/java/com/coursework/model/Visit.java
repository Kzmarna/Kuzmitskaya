package com.coursework.model;

/**
 * Class-entity for student visit
 * @author Kuzmitskaya Maryia
 * @version 1.0
 */
public class Visit {
    private int id;
    private int dateId;
    private int studentId;

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
     * Getter for date id
     * @return date id
     */
    public int getDateId() {
        return dateId;
    }

    /**
     * Setter for date id
     * @param dateId
     */
    public void setDateId(int dateId) {
        this.dateId = dateId;
    }

    /**
     * Getter for student id
     * @return student id
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * Setter for student id
     * @param studentId
     */
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}
