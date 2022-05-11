package com.coursework.view;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Class for adding an image
 * @author Kuzmitskaya Maryia
 * @version 1.0
 */
public class ReaderImage {
    /**
     * Method for getting an image
     * @param path path
     * @param sizeX size x
     * @param sizeY size y
     * @return image
     */
    public ImageIcon scaleImage(String path, int sizeX, int sizeY) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        ImageIcon icon  = new ImageIcon(Objects.requireNonNull(classLoader.getResource("resources/" + path)));
        Image newImageIcon = icon.getImage().getScaledInstance(sizeX,sizeY,Image.SCALE_DEFAULT);
        icon.setImage(newImageIcon);
        return icon;
    }
}
