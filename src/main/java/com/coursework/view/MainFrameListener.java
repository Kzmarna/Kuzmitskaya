package com.coursework.view;

import com.coursework.model.Dates;
import com.coursework.model.Groups;
import com.coursework.model.Student;
import com.coursework.model.Visit;
import com.coursework.service.StudentServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainFrameListener extends JFrame {
    public MainFrameListener() {
        JFrame frame = new JFrame("Lectures");
        frame.setLayout(new BoxLayout(frame, BoxLayout.X_AXIS));
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(new Dimension(1500, 800));
        JPanel contents = new JPanel();
        contents.setLayout(new BoxLayout(contents, BoxLayout.Y_AXIS));
        JPanel studentsList = new JPanel();
        studentsList.setPreferredSize(new Dimension(700, 800));
        JPanel groupsButtons = new JPanel();

        createButtonsForGroups(groupsButtons, studentsList);
        contents.add(groupsButtons);

        contents.add(studentsList);
        frame.setContentPane(contents);
        frame.pack();
        frame.setVisible(true);
    }

    private static void addGroupButton(String caption, Container containerButton, Container containerStudents, ButtonGroup buttonGroup) {
        JButton button = new JButton(caption);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setAlignmentY(Component.TOP_ALIGNMENT);
        button.addActionListener(e -> {
            Groups group = StudentServiceImpl.getInstance().getGroup(button.getText());
            createStudentsPane(containerStudents, group);
        });

        buttonGroup.add(button);
        containerButton.add(button);
    }

    public static void createButtonsForGroups(Container containerButton, Container containerStudents) {
        containerButton.setLayout(new BoxLayout(containerButton, BoxLayout.X_AXIS));
        List<Groups> allGroups = StudentServiceImpl.getInstance().getAllGroups();
        ButtonGroup buttonGroup = new ButtonGroup();
        for (Groups group : allGroups) {
            addGroupButton(group.getGroupNumber(), containerButton, containerStudents, buttonGroup);
        }
    }

    public static void createStudentsPane(Container container, Groups group) {
        JTable studentsTable = new JTable(createTableData(group), createHeaderTable()) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 0) {
                    return String.class;
                }
                return Boolean.class;
            }
            @Override
            public void setValueAt(Object value, int row, int col) {
                super.setValueAt(value, row, col);
                if (col > 0) {
                    String studentName = (String) getValueAt(row, 0);
                    String date = createHeaderTable()[col];
                    Student student = new Student();
                    student.setStudentLastname(studentName.substring(0, studentName.indexOf(" ")));
                    studentName = studentName.substring(studentName.indexOf(" ") + 1);
                    student.setStudentName(studentName.substring(0, studentName.indexOf(" ")));
                    studentName = studentName.substring(studentName.indexOf(" ") + 1);
                    student.setStudentPatronymic(studentName);
                    if ((Boolean) this.getValueAt(row, col)) {
                        Visit visit = new Visit();
                        StudentServiceImpl.getInstance().addVisit(date, student, visit);
                    }
                    else {
                        Dates deleteDate = new Dates();
                        for (Student allStudent: StudentServiceImpl.getInstance().getStudentsByGroup(group)) {
                            if (allStudent.getStudentLastname().equals(student.getStudentLastname())) {
                                student.setId(allStudent.getId());
                            }
                        }
                        for (Dates allDate: StudentServiceImpl.getInstance().getAllDates()) {
                            if (allDate.getDate().equals(date)) {
                                deleteDate.setId(allDate.getId());
                                StudentServiceImpl.getInstance().deleteVisit(deleteDate.getId(), student.getId());
                            }
                        }
                    }
                }
            }
        };
        JScrollPane scrollPane = new JScrollPane(studentsTable);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(600, 700));
        scrollPane.setMaximumSize(scrollPane.getSize());
        scrollPane.setMinimumSize(scrollPane.getSize());
        container.removeAll();
        container.add(scrollPane);

        container.repaint();
        container.revalidate();

    }

    private static String[] createListOfDates() {
        List<Dates> dates = StudentServiceImpl.getInstance().getAllDates();
        String[] datesOfVisit = new String[dates.size()];
        int j = 0;
        for (Dates date : dates) {
            datesOfVisit[j] = date.getDate();
            j++;
        }
        return datesOfVisit;
    }

    private static String[] createStudentsList(Groups group) {
        List<Student> studentList = StudentServiceImpl.getInstance().getStudentsByGroup(group);
        String[] students = new String[studentList.size()];
        int i = 0;
        for (Student student : studentList) {
            students[i] = student.getStudentLastname() + " " +
                    student.getStudentName() + " " +
                    student.getStudentPatronymic();
            i++;
        }
        return students;
    }

    private static String[] createHeaderTable() {
        String[] dates = createListOfDates();
        String[] header = new String[dates.length + 1];
        header[0] = "ФИО";
        System.arraycopy(dates, 0, header, 1, header.length - 1);
        return header;
    }

    private static Object[][] createTableData(Groups group) {
        String[] students = createStudentsList(group);
        List<Student> studentsDb = StudentServiceImpl.getInstance().getStudentsByGroup(group);
        String[] dates = createListOfDates();
        List<Dates> datesDb = StudentServiceImpl.getInstance().getAllDates();
        Object[][] tableData = new Object[students.length][datesDb.size() + 1];
        int i = 0;
        for (Student student : studentsDb) {
            List<Dates> visits = StudentServiceImpl.getInstance().getDatesByStudent(student);
            tableData[i][0] = students[i];
            int k = 0;
            for (int j = 1; j < dates.length + 1; j++) {
                tableData[i][j] = checkDate(visits, dates[k]);
                k++;
            }
            i++;
        }
        return tableData;
    }
    private static boolean checkDate(List<Dates> datesDb, String dateString) {
        for (Dates date: datesDb) {
            if (date.getDate().equals(dateString)) return true;
        }
        return false;
    }
}
