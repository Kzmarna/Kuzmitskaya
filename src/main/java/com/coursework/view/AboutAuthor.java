package com.coursework.view;

import javax.swing.*;
import java.awt.*;

public class AboutAuthor extends JFrame {

    public AboutAuthor() {
        JFrame frame = new JFrame("Об авторе");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(550, 650));
        frame.setResizable(false);
        frame.setLocation(400, 100);

        Container container = new Container();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        ReaderImage readerImage = new ReaderImage();
        Icon icon = readerImage.scaleImage("images/cat1.png", 368, 400);

        JPanel image = new JPanel();
        image.setMaximumSize(new Dimension(550, 450));
        image.setMinimumSize(new Dimension(550, 450));

        JLabel iconLabel = new JLabel();
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        iconLabel.setIcon(icon);

        JLabel author = new JLabel();
        author.setText("Кузьмицкая Мария Васильевна");
        author.setAlignmentX(Component.CENTER_ALIGNMENT);
        author.setFont(new Font("Times New Roman", Font.PLAIN, 25));

        JLabel group = new JLabel();
        group.setText("студентка группы 10702419");
        group.setAlignmentX(Component.CENTER_ALIGNMENT);
        group.setFont(new Font("Times New Roman", Font.PLAIN, 25));

        JLabel info = new JLabel();
        info.setText("grisdeborah44@gmail.com");
        info.setAlignmentX(Component.CENTER_ALIGNMENT);
        info.setFont(new Font("Times New Roman", Font.PLAIN, 25));

        JButton back = new JButton("Назад");
        back.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        back.setAlignmentX(Component.CENTER_ALIGNMENT);
        back.setMinimumSize(new Dimension(100,40));
        back.setMaximumSize(new Dimension(100,40));
        back.addActionListener(e -> {
            frame.dispose();
            new MainFrameListener();
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
