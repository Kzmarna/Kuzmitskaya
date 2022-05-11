package com.coursework.view;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Date formatting class
 * @author Kuzmitskaya Maryia
 * @version 1.0
 */
public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

    private final String datePattern = "dd/MM/yyyy";
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    /**
     * Method for converting a string to a date
     * @param text date string
     * @return date
     * @throws ParseException
     */
    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    /**
     * Method for converting a date to a string
     * @param value date
     * @return date string
     */
    @Override
    public String valueToString(Object value) {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }

}