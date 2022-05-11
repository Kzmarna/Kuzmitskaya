package com.coursework.view;

import javax.swing.*;
import java.awt.*;

/**
 * About the Author window
 * @author Kuzmitskaya Maryia
 * @version 1.0
 */
public class AboutAuthor extends JFrame {

    private final JFrame frame;
    private Container container;
    private ReaderImage readerImage;
    private Icon icon;
    private JPanel image;
    private JLabel iconLabel;
    private JLabel author;
    private JLabel group;
    private JLabel info;
    private JButton back;

    /**
     * Constructor
     */
    public AboutAuthor() {
        frame = new JFrame("Об авторе");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(550, 650));
        frame.setResizable(false);
        frame.setLocation(400, 100);

        container = new Container();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        readerImage = new ReaderImage();
        icon = readerImage.scaleImage("images/cat1.png", 368, 400);

        image = new JPanel();
        image.setMaximumSize(new Dimension(550, 450));
        image.setMinimumSize(new Dimension(550, 450));

        iconLabel = new JLabel();
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        iconLabel.setIcon(icon);

        author = new JLabel();
        author.setText("Кузьмицкая Мария Васильевна");
        author.setAlignmentX(Component.CENTER_ALIGNMENT);
        author.setFont(new Font("Times New Roman", Font.PLAIN, 25));

        group = new JLabel();
        group.setText("студентка группы 10702419");
        group.setAlignmentX(Component.CENTER_ALIGNMENT);
        group.setFont(new Font("Times New Roman", Font.PLAIN, 25));

        info = new JLabel();
        info.setText("grisdeborah44@gmail.com");
        info.setAlignmentX(Component.CENTER_ALIGNMENT);
        info.setFont(new Font("Times New Roman", Font.PLAIN, 25));

        back = new JButton("Назад");
        back.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        back.setAlignmentX(Component.CENTER_ALIGNMENT);
        back.setMinimumSize(new Dimension(100,40));
        back.setMaximumSize(new Dimension(100,40));
        back.addActionListener(e -> {
            frame.dispose();
            new MainFrame();
        });

        image.add(iconLabel);

        container.add(image);
        container.add(author);
        container.add(group);
        container.add(info);
        container.add(back);

        frame.setContentPane(container);
        frame.pack();
        frame.setVisible(true);
    }
}
