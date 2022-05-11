package com.coursework.view;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ReaderImage {
    public ImageIcon scaleImage(String path, int sizeX, int sizeY) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        ImageIcon icon  = new ImageIcon(Objects.requireNonNull(classLoader.getResource("resources/" + path)));
        Image newImageIcon = icon.getImage().getScaledInstance(sizeX,sizeY,Image.SCALE_DEFAULT);
        icon.setImage(newImageIcon);
        return icon;
    }
}
