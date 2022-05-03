package com.coursework.view;

import com.coursework.model.Dates;
import com.coursework.model.Groups;
import com.coursework.model.Student;
import com.coursework.model.Visit;
import com.coursework.service.StudentServiceImpl;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

public class MainFrameListener extends JFrame {
    public MainFrameListener() {
        Container container = new Container();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

        JFrame frame = new JFrame("Lectures");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(new Dimension(1500, 800));

        JPanel contents = new JPanel();
        contents.setLayout(new BoxLayout(contents, BoxLayout.Y_AXIS));

        JPanel studentsList = new JPanel();
        studentsList.setPreferredSize(new Dimension(700, 800));

        JPanel groupsButtons = new JPanel();

        JPanel modification = new JPanel();
        modification.setLayout(new BoxLayout(modification, BoxLayout.Y_AXIS));

        JPanel dateOfVisit = new JPanel();
        dateOfVisit.setBorder(BorderFactory.createTitledBorder("Добавить дату посещения"));
        dateOfVisit.setPreferredSize(new Dimension(350, 250));

        JPanel studentsByDate = new JPanel();
        studentsByDate.setBorder(BorderFactory.createTitledBorder("Список студентов по дате посещения"));
        studentsByDate.setPreferredSize(new Dimension(350, 250));

        JPanel groupsModification = new JPanel();
        groupsModification.setBorder(BorderFactory.createTitledBorder("Добавление/Удаление группы"));
        groupsModification.setPreferredSize(new Dimension(350, 250));

        JPanel studentsModification = new JPanel();
        studentsModification.setBorder(BorderFactory.createTitledBorder("Добавление/Удаление студента"));
        studentsModification.setPreferredSize(new Dimension(350, 250));

        createPanelForAddDeleteStudent(studentsModification);
        createPanelForAddDeleteGroup(groupsModification, groupsButtons, studentsList);
        createPanelForAddDate(dateOfVisit);
        createButtonsForGroups(groupsButtons, studentsList);
        createPanelForGetStudentsByDate(studentsByDate, studentsList);

        contents.add(groupsButtons);
        contents.add(studentsList);

        container.add(contents);
        modification.add(dateOfVisit);
        modification.add(studentsByDate);
        modification.add(groupsModification);
        modification.add(studentsModification);
        container.add(modification);

        frame.setContentPane(container);
        frame.pack();
        frame.setVisible(true);
    }

    private static void createPanelForAddDeleteStudent(Container studentModification) {
        JTextField studentsName = new JTextField(30);
        JTextField studentGroup = new JTextField(15);
        studentModification.add(studentsName);
        studentModification.add(studentGroup);
        JButton addStudentButton = new JButton("Добавить");
        JButton deleteStudentButton = new JButton("Удалить");
        addStudentButton.addActionListener(e -> {
            String studentName = studentsName.getText();
            Student student = new Student();
            student.setStudentLastname(studentName.substring(0, studentName.indexOf(" ")));
            studentName = studentName.substring(studentName.indexOf(" ") + 1);
            student.setStudentName(studentName.substring(0, studentName.indexOf(" ")));
            studentName = studentName.substring(studentName.indexOf(" ") + 1);
            student.setStudentPatronymic(studentName);
            Groups group = new Groups();
            group.setGroupNumber(studentGroup.getText());
            StudentServiceImpl.getInstance().addStudent(student, group);
        });
        deleteStudentButton.addActionListener(e -> {
            String studentName = studentsName.getText();
            Student student = new Student();
            student.setStudentLastname(studentName.substring(0, studentName.indexOf(" ")));
            studentName = studentName.substring(studentName.indexOf(" ") + 1);
            student.setStudentName(studentName.substring(0, studentName.indexOf(" ")));
            studentName = studentName.substring(studentName.indexOf(" ") + 1);
            student.setStudentPatronymic(studentName);
            Groups group = new Groups();
            group.setGroupNumber(studentGroup.getText());
            Student studentDelete = StudentServiceImpl.getInstance().getStudentByName(student);
            StudentServiceImpl.getInstance().deleteStudent(studentDelete);
        });
        studentModification.add(addStudentButton);
        studentModification.add(deleteStudentButton);
    }

    private static void createPanelForAddDeleteGroup(Container groupModification, Container groupsButtons, Container studentsList) {
        JTextField groupField = new JTextField(15);
        groupModification.add(groupField);
        JButton addGroupButton = new JButton("Добавить");
        JButton deleteGroupButton = new JButton("Удалить");
        addGroupButton.addActionListener(e -> {
            Groups newGroup = new Groups();
            newGroup.setGroupNumber(groupField.getText());
            StudentServiceImpl.getInstance().addGroup(newGroup);
            groupsButtons.removeAll();
            List<Groups> allGroups = StudentServiceImpl.getInstance().getAllGroups();
            ButtonGroup buttonGroup = new ButtonGroup();
            for (Groups group : allGroups) {
                addGroupButton(group.getGroupNumber(), groupsButtons, studentsList, buttonGroup);
            }
            groupsButtons.repaint();
            groupsButtons.revalidate();
        });
        deleteGroupButton.addActionListener(e -> {
            StudentServiceImpl.getInstance().deleteGroup(groupField.getText());
            groupsButtons.removeAll();
            List<Groups> allGroups = StudentServiceImpl.getInstance().getAllGroups();
            ButtonGroup buttonGroup = new ButtonGroup();
            for (Groups group : allGroups) {
                addGroupButton(group.getGroupNumber(), groupsButtons, studentsList, buttonGroup);
            }
            groupsButtons.repaint();
            groupsButtons.revalidate();
        });
        groupModification.add(addGroupButton);
        groupModification.add(deleteGroupButton);
    }

    private static void createPanelForGetStudentsByDate(Container studentsByDate, Container studentsList) {
        JDatePickerImpl datePicker = createDatePicker();
        JButton getStudentsByDateButton = new JButton("Поиск");
        getStudentsByDateButton.addActionListener(e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Dates date = new Dates();
            if (datePicker.getModel().getValue() != null)
                date.setDate(sdf.format(datePicker.getModel().getValue()));
            List<Student> studentList = StudentServiceImpl.getInstance().getStudentsByDate(date);
            String[] studentsName = new String[studentList.size()];
            int i = 0;
            for (Student student : studentList) {
                Groups group = StudentServiceImpl.getInstance().getGroupById(student.getGroupId());
                studentsName[i] = student.getStudentLastname() + " " + student.getStudentName() + " " + student.getStudentPatronymic() + " " + group.getGroupNumber();
                i++;
            }
            JList<String> students = new JList<String>(studentsName);
            JScrollPane scrollPane = new JScrollPane(students);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setPreferredSize(new Dimension(600, 700));
            scrollPane.setMaximumSize(scrollPane.getSize());
            scrollPane.setMinimumSize(scrollPane.getSize());
            studentsList.removeAll();
            studentsList.add(scrollPane);
            studentsList.repaint();
            studentsList.revalidate();
        });
        studentsByDate.add(datePicker);
        studentsByDate.add(getStudentsByDateButton);
    }

    private static void createPanelForAddDate(Container dateOfVisit) {
        JDatePickerImpl datePicker = createDatePicker();
        dateOfVisit.add(datePicker);
        JButton addDateButton = new JButton("Добавить");
        addDateButton.addActionListener(e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Dates date = new Dates();
            if (datePicker.getModel().getValue() != null)
                date.setDate(sdf.format(datePicker.getModel().getValue()));
            StudentServiceImpl.getInstance().addDate(date);
        });
        dateOfVisit.add(addDateButton);
    }

    private static JDatePickerImpl createDatePicker() {
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        return new JDatePickerImpl(datePanel, new DateLabelFormatter());
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
                    } else {
                        Dates deleteDate = new Dates();
                        for (Student allStudent : StudentServiceImpl.getInstance().getStudentsByGroup(group)) {
                            if (allStudent.getStudentLastname().equals(student.getStudentLastname())) {
                                student.setId(allStudent.getId());
                            }
                        }
                        for (Dates allDate : StudentServiceImpl.getInstance().getAllDates()) {
                            if (allDate.getDate().equals(date)) {
                                deleteDate.setId(allDate.getId());
                                StudentServiceImpl.getInstance().deleteVisit(deleteDate.getId(), student.getId());
                            }
                        }
                    }
                }
            }
        };
        studentsTable.setAutoCreateRowSorter(true);
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
        for (Dates date : datesDb) {
            if (date.getDate().equals(dateString)) return true;
        }
        return false;
    }
}
