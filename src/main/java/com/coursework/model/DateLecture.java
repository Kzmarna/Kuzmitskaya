package com.coursework.model;

/**
 * Class-entity for lecture dates
 * @author Kuzmitskaya Maryia
 * @version 1.0
 */
public class DateLecture {
    private int id;
    private String date;

    /**
     * Getter for date
     * @return id of date
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for date
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for date
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * Setter for date
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

}
