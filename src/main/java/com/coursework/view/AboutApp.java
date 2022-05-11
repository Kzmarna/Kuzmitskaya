package com.coursework.view;

import javax.swing.*;
import java.awt.*;

/**
 * About the Application window
 * @author Kuzmitskaya Maryia
 * @version 1.0
 */
public class AboutApp extends JFrame {

    private JFrame frame;
    private Container container;
    private JPanel theme;
    private JPanel info;
    private JPanel image;
    private JPanel infoText;
    private ReaderImage readerImage;
    private Icon icon;
    private JLabel iconLabel;
    private JLabel courseTheme;
    private JLabel label;
    private JLabel functionNumber1;
    private JLabel functionNumber2;
    private JLabel functionNumber3;
    private JLabel functionNumber4;
    private JLabel functionNumber5;
    private JLabel functionNumber6;
    private JButton back;

    /**
     * Constructor
     */
    public AboutApp() {
        frame = new JFrame("О программе");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(900, 550));
        frame.setResizable(false);
        frame.setLocation(400, 100);

        container = new Container();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        theme = new JPanel();
        theme.setMaximumSize(new Dimension(900, 85));
        theme.setMinimumSize(new Dimension(900, 85));

        info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.X_AXIS));
        info.setMaximumSize(new Dimension(900, 280));
        info.setMinimumSize(new Dimension(900, 280));

        image = new JPanel();
        image.setMaximumSize(new Dimension(350, 450));
        image.setMinimumSize(new Dimension(350, 450));

        infoText = new JPanel();
        infoText.setLayout(new FlowLayout(FlowLayout.LEFT));
        infoText.setMaximumSize(new Dimension(400, 450));
        infoText.setMinimumSize(new Dimension(400, 450));

        readerImage = new ReaderImage();
        icon = readerImage.scaleImage("images/about_app1.png", 250, 242);

        iconLabel = new JLabel();
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        iconLabel.setIcon(icon);

        courseTheme = new JLabel();
        courseTheme.setText("Посещение лекционных занятий");
        courseTheme.setAlignmentX(Component.CENTER_ALIGNMENT);
        courseTheme.setFont(new Font("Times New Roman", Font.PLAIN, 30));

        label = new JLabel();
        label.setText("Приложение позволяет");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Times New Roman", Font.PLAIN, 25));

        functionNumber1 = new JLabel();
        functionNumber1.setText("1. Контролировать посещаемость студентов.");
        functionNumber1.setAlignmentX(Component.CENTER_ALIGNMENT);
        functionNumber1.setFont(new Font("Times New Roman", Font.PLAIN, 25));

        functionNumber2 = new JLabel();
        functionNumber2.setText("2. Добавлять/Удалять группы.");
        functionNumber2.setAlignmentX(Component.CENTER_ALIGNMENT);
        functionNumber2.setFont(new Font("Times New Roman", Font.PLAIN, 25));

        functionNumber3 = new JLabel();
        functionNumber3.setText("3. Добавлять/Удалять студентов.");
        functionNumber3.setAlignmentX(Component.CENTER_ALIGNMENT);
        functionNumber3.setFont(new Font("Times New Roman", Font.PLAIN, 25));

        functionNumber4 = new JLabel();
        functionNumber4.setText("4. Добавлять даты посещений.");
        functionNumber4.setAlignmentX(Component.CENTER_ALIGNMENT);
        functionNumber4.setFont(new Font("Times New Roman", Font.PLAIN, 25));

        functionNumber5 = new JLabel();
        functionNumber5.setText("5. Сохранять результат в excel таблицы.");
        functionNumber5.setAlignmentX(Component.CENTER_ALIGNMENT);
        functionNumber5.setFont(new Font("Times New Roman", Font.PLAIN, 25));

        functionNumber6 = new JLabel();
        functionNumber6.setText("6. Сортировать список студентов.");
        functionNumber6.setAlignmentX(Component.CENTER_ALIGNMENT);
        functionNumber6.setFont(new Font("Times New Roman", Font.PLAIN, 25));

        back = new JButton("Назад");
        back.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        back.setAlignmentX(Component.CENTER_ALIGNMENT);
        back.setMinimumSize(new Dimension(170,70));
        back.setMaximumSize(new Dimension(170,70));
        back.addActionListener(e -> {
            frame.dispose();
            new MainFrame();
        });

        infoText.add(label);
        infoText.add(functionNumber1);
        infoText.add(functionNumber2);
        infoText.add(functionNumber3);
        infoText.add(functionNumber4);
        infoText.add(functionNumber5);
        infoText.add(functionNumber6);

        theme.add(courseTheme);
        info.add(iconLabel);
        info.add(infoText);

        container.add(theme);
        container.add(info);
        container.add(back);

        frame.setContentPane(container);
        frame.pack();
        frame.setVisible(true);
    }
}
