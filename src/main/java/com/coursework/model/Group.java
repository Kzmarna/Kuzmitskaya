package com.coursework.model;

/**
 * Class-entity for group of students
 * @author Kuzmitskaya Maryia
 * @version 1.0
 */
public class Group {
    private int id;
    private String groupNumber;

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
     * Getter for group number
     * @return group number
     */
    public String getGroupNumber() {
        return groupNumber;
    }

    /**
     * Setter for group number
     * @param groupNumber
     */
    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }
}
