package com.coursework.view;

import javax.swing.*;
import java.awt.*;

/**
 * Splash screen window
 * @author Kuzmitskaya Maryia
 * @version 1.0
 */
public class SplashScreen extends JFrame{

    private final JFrame frame;
    private Container container;
    private JPanel universityPanel;
    private JPanel facultyPanel;
    private JPanel courseWorkPanel;
    private JPanel courseWorkInformation;
    private JPanel information;
    private JPanel authorInformation;
    private JPanel teacherInformation;
    private JPanel iconPanel;
    private JPanel buttons;
    private JLabel university;
    private JLabel faculty;
    private JLabel department;
    private JLabel courseWork;
    private JLabel discipline;
    private JLabel theme;
    private JLabel author;
    private JLabel authorName;
    private JLabel teacherName;
    private JLabel teacher;
    private JLabel minsk2022;
    private ReaderImage readerImage;
    private JLabel imageLabel;
    private Icon icon;
    private JButton start;
    private JButton exit;
    private Timer timer;

    /**
     * Constructor
     */
    public SplashScreen(){
        frame = new JFrame("Lectures");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setPreferredSize(new Dimension(1100, 720));
        frame.setLocation(400, 100);

        container = new Container();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        universityPanel = new JPanel();
        universityPanel.setMaximumSize(new Dimension(1100, 45));
        universityPanel.setMinimumSize(new Dimension(1100, 45));

        facultyPanel = new JPanel();
        facultyPanel.setMaximumSize(new Dimension(1100, 150));
        facultyPanel.setMinimumSize(new Dimension(1100, 150));

        courseWorkPanel = new JPanel();
        courseWorkPanel.setLayout(new BoxLayout(courseWorkPanel, BoxLayout.Y_AXIS));
        courseWorkPanel.setMaximumSize(new Dimension(1100, 170));
        courseWorkPanel.setMinimumSize(new Dimension(1100, 170));

        courseWorkInformation = new JPanel();
        courseWorkInformation.setLayout(new BoxLayout(courseWorkInformation, BoxLayout.X_AXIS));
        courseWorkInformation.setMaximumSize(new Dimension(1100, 190));
        courseWorkInformation.setMinimumSize(new Dimension(1100, 190));

        information = new JPanel();
        information.setLayout(new BoxLayout(information, BoxLayout.Y_AXIS));
        information.setMaximumSize(new Dimension(550, 190));
        information.setMinimumSize(new Dimension(550, 190));

        authorInformation = new JPanel();
        authorInformation.setLayout(new BoxLayout(authorInformation, BoxLayout.Y_AXIS));
        authorInformation.setMaximumSize(new Dimension(550, 95));
        authorInformation.setMinimumSize(new Dimension(550, 95));

        teacherInformation = new JPanel();
        teacherInformation.setLayout(new BoxLayout(teacherInformation, BoxLayout.Y_AXIS));
        teacherInformation.setMaximumSize(new Dimension(550, 95));
        teacherInformation.setMinimumSize(new Dimension(550, 95));

        iconPanel = new JPanel();
        iconPanel.setMaximumSize(new Dimension(550, 190));
        iconPanel.setMinimumSize(new Dimension(550, 190));

        buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));

        university = new JLabel();
        university.setText("?????????????????????? ???????????????????????? ?????????????????????? ??????????????????????");
        university.setAlignmentX(Component.CENTER_ALIGNMENT);
        university.setFont(new Font("Times New Roman", Font.PLAIN, 24));

        faculty = new JLabel();
        faculty.setText("?????????????????? ???????????????????????????? ???????????????????? ?? ??????????????????????????");
        faculty.setAlignmentX(Component.CENTER_ALIGNMENT);
        faculty.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        department = new JLabel();
        department.setText("?????????????? ???????????????????????? ?????????????????????? ???????????????????????????? ???????????? ?? ????????????????????");
        department.setAlignmentX(Component.CENTER_ALIGNMENT);
        department.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        courseWork = new JLabel();
        courseWork.setText("???????????????? ????????????");
        courseWork.setAlignmentX(Component.CENTER_ALIGNMENT);
        courseWork.setFont(new Font("Times New Roman", Font.PLAIN, 30));

        discipline = new JLabel();
        discipline.setText("???? ???????????????????? ?????????????????????????????????? ???? ?????????? Java??");
        discipline.setAlignmentX(Component.CENTER_ALIGNMENT);
        discipline.setFont(new Font("Times New Roman", Font.PLAIN, 25));

        theme = new JLabel();
        theme.setText("???????????????????????? ???????????????????? ??????????????");
        theme.setAlignmentX(Component.CENTER_ALIGNMENT);
        theme.setFont(new Font("Times New Roman", Font.PLAIN, 40));

        author = new JLabel();
        author.setText("??????????????????: ?????????????????? ???????????? 10702419");
        author.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        authorName = new JLabel();
        authorName.setText("???????????????????? ?????????? ????????????????????");
        authorName.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        teacherName = new JLabel();
        teacherName.setText("?????????????? ?????????????? ????????????????????????");
        teacherName.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        teacher = new JLabel();
        teacher.setText("??????????????????????????: ??.??.-??.??.,??????.");
        teacher.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        minsk2022 = new JLabel();
        minsk2022.setText("??????????, 2022");
        minsk2022.setAlignmentX(Component.CENTER_ALIGNMENT);
        minsk2022.setFont(new Font("Times New Roman", Font.PLAIN, 25));

        readerImage = new ReaderImage();
        icon = readerImage.scaleImage("images/splash_screen_image1.png", 150, 146);

        imageLabel = new JLabel();
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageLabel.setIcon(icon);

        start = new JButton("??????????");
        start.addActionListener(e -> {
            frame.dispose();
            timer.stop();
            new MainFrame();
        });
        start.setMinimumSize(new Dimension(100,40));
        start.setMaximumSize(new Dimension(100,40));
        exit = new JButton("??????????");
        exit.addActionListener(e -> System.exit(0));
        exit.setMinimumSize(new Dimension(100,40));
        exit.setMaximumSize(new Dimension(100,40));

        timer = new Timer(60000, e -> System.exit(0));
        timer.setRepeats(false);
        timer.start();

        universityPanel.add(university);
        facultyPanel.add(faculty);
        facultyPanel.add(department);
        courseWorkPanel.add(courseWork);
        courseWorkPanel.add(discipline);
        courseWorkPanel.add(theme);
        authorInformation.add(author);
        authorInformation.add(authorName);
        teacherInformation.add(teacher);
        teacherInformation.add(teacherName);
        information.add(authorInformation);
        information.add(teacherInformation);
        iconPanel.add(imageLabel);
        courseWorkInformation.add(iconPanel);
        courseWorkInformation.add(information);
        buttons.add(start);
        buttons.add(exit);

        container.add(universityPanel);
        container.add(facultyPanel);
        container.add(courseWorkPanel);
        container.add(courseWorkInformation);
        container.add(minsk2022);
        container.add(buttons);

        frame.setContentPane(container);
        frame.pack();
        frame.setVisible(true);
    }
}
