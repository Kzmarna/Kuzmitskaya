package com.coursework.view;

import com.coursework.model.DateLecture;
import com.coursework.model.Group;
import com.coursework.model.Student;
import com.coursework.model.Visit;
import com.coursework.service.FileService;
import com.coursework.service.FileServiceImpl;
import com.coursework.service.StudentService;
import com.coursework.service.StudentServiceImpl;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

public class MainFrameListener extends JFrame {

    private final FileService fileService;
    private final StudentService studentService;
    private Group currentGroup;

    public MainFrameListener() {
        fileService = FileServiceImpl.getInstance();
        studentService = StudentServiceImpl.getInstance();

        Container container = new Container();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

        JFrame frame = new JFrame("Lectures");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1300, 1000));

        JPanel contents = new JPanel();
        contents.setLayout(new BoxLayout(contents, BoxLayout.Y_AXIS));

        JPanel studentsList = new JPanel();
        studentsList.setSize(new Dimension(700, 700));

        JPanel groupsButtons = new JPanel();
        groupsButtons.setMinimumSize(new Dimension(700, 100));
        groupsButtons.setMaximumSize(new Dimension(700, 100));

        JPanel modification = new JPanel();
        modification.setLayout(new BoxLayout(modification, BoxLayout.Y_AXIS));
        modification.setMinimumSize(new Dimension(400, 700));
        modification.setMaximumSize(new Dimension(400, 700));

        JPanel dateOfVisit = new JPanel();
        dateOfVisit.setLayout(new FlowLayout(FlowLayout.CENTER));
        dateOfVisit.setBorder(BorderFactory.createTitledBorder("Добавить дату посещения"));
        dateOfVisit.setPreferredSize(new Dimension(350, 150));

        JPanel studentsByDate = new JPanel();
        studentsByDate.setLayout(new FlowLayout(FlowLayout.CENTER));
        studentsByDate.setBorder(BorderFactory.createTitledBorder("Список студентов по дате посещения"));
        studentsByDate.setPreferredSize(new Dimension(350, 150));

        JPanel groupsModification = new JPanel();
        groupsModification.setLayout(new FlowLayout(FlowLayout.CENTER));
        groupsModification.setBorder(BorderFactory.createTitledBorder("Добавление/Удаление группы"));
        groupsModification.setPreferredSize(new Dimension(350, 150));

        JPanel studentsModification = new JPanel();
        studentsModification.setLayout(new FlowLayout(FlowLayout.CENTER));
        studentsModification.setBorder(BorderFactory.createTitledBorder("Добавление/Удаление студента"));
        studentsModification.setPreferredSize(new Dimension(350, 150));

        JPanel filePanel = new JPanel();
        JButton fileButton = new JButton("Сохранить в файл");
        fileButton.addActionListener(e -> {
            if (currentGroup != null) {
                File file = fileService.createFile(studentsList);
                fileService.writeDataToWord(file, studentService.getStudentsByGroup(currentGroup));
            } else {
                JOptionPane.showMessageDialog(MainFrameListener.this,
                        new String[]{"Вы не выбрали группу!"},
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
        filePanel.add(fileButton);

        createPanelForAddDeleteStudent(studentsModification);
        createPanelForAddDeleteGroup(groupsModification, groupsButtons, studentsList);
        createPanelForAddDate(dateOfVisit);
        createButtonsForGroups(groupsButtons, studentsList);
        createPanelForGetStudentsByDate(studentsByDate, studentsList);

        contents.add(groupsButtons);
        contents.add(studentsList);
        contents.add(filePanel);

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

    private void createPanelForAddDeleteStudent(Container studentModification) {
        JLabel enterStudentName = new JLabel("Введите ФИО:");
        JTextField studentsName = new JTextField(25);
        JLabel enterStudentGroup = new JLabel("Введите номер группы:");
        JTextField studentGroup = new JTextField(25);
        studentModification.add(enterStudentName);
        studentModification.add(studentsName);
        studentModification.add(enterStudentGroup);
        studentModification.add(studentGroup);
        JButton addStudentButton = new JButton("Добавить");
        JButton deleteStudentButton = new JButton("Удалить");
        addStudentButton.addActionListener(e -> {
            if (studentsName.getText().isEmpty() || studentGroup.getText().isEmpty()) {
                JOptionPane.showMessageDialog(MainFrameListener.this,
                        new String[]{"Вы не заполнили все поля!"},
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            } else if (checkFullStudentName(studentsName.getText()) == null) {
                JOptionPane.showMessageDialog(MainFrameListener.this,
                        new String[]{"Вы ввели некорректное ФИО студента!"},
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            } else if (studentService.getGroup(studentGroup.getText()) == null) {
                JOptionPane.showMessageDialog(MainFrameListener.this,
                        new String[]{"Такой группы не существует!"},
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                String studentName = studentsName.getText();
                Student student = checkFullStudentName(studentName);
                Group group = new Group();
                group.setGroupNumber(studentGroup.getText());
                studentService.addStudent(student, group);
            }
        });
        deleteStudentButton.addActionListener(e ->
        {
            if (studentsName.getText().isEmpty() || studentGroup.getText().isEmpty()) {
                JOptionPane.showMessageDialog(MainFrameListener.this,
                        new String[]{"Вы не заполнили все поля!"},
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            } else if (checkFullStudentName(studentsName.getText()) == null) {
                JOptionPane.showMessageDialog(MainFrameListener.this,
                        new String[]{"Вы ввели некорректное ФИО студента!"},
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            } else if (studentService.getGroup(studentGroup.getText()) == null) {
                JOptionPane.showMessageDialog(MainFrameListener.this,
                        new String[]{"Такой группы не существует!"},
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                String studentName = studentsName.getText();
                Student student = checkFullStudentName(studentName);
                if (student != null) {
                    student.setGroupId(studentService.getGroup(studentGroup.getText()).getId());
                    Student studentDelete = studentService.getStudentByName(student);
                    studentService.deleteStudent(studentDelete);
                } else {
                    JOptionPane.showMessageDialog(MainFrameListener.this,
                            new String[]{"Такого студента не существует!"},
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        studentModification.add(addStudentButton);
        studentModification.add(deleteStudentButton);
    }

    private Student checkFullStudentName(String studentName) {
        try {
            Student student = new Student();
            student.setStudentLastname(studentName.substring(0, studentName.indexOf(" ")));
            studentName = studentName.substring(studentName.indexOf(" ") + 1);
            student.setStudentName(studentName.substring(0, studentName.indexOf(" ")));
            studentName = studentName.substring(studentName.indexOf(" ") + 1);
            student.setStudentPatronymic(studentName);
            return student;
        } catch (Exception exception) {
            return null;
        }
    }

    private void createPanelForAddDeleteGroup(Container groupModification, Container groupsButtons, Container studentsList) {
        JLabel enterGroupNumber = new JLabel("Введите номер группы:");
        JTextField groupField = new JTextField(25);
        groupModification.add(enterGroupNumber);
        groupModification.add(groupField);
        JButton addGroupButton = new JButton("Добавить");
        JButton deleteGroupButton = new JButton("Удалить");
        addGroupButton.addActionListener(e -> {
            if (groupField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(MainFrameListener.this,
                        new String[]{"Вы не заполнили поле!"},
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            } else if (!checkGroup(groupField.getText())) {
                JOptionPane.showMessageDialog(MainFrameListener.this,
                        new String[]{"Такая группа уже существует!"},
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                Group newGroup = new Group();
                newGroup.setGroupNumber(groupField.getText());
                studentService.addGroup(newGroup);
                updateGroupsButtons(groupsButtons, studentsList);
            }
        });
        deleteGroupButton.addActionListener(e -> {
            if (groupField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(MainFrameListener.this,
                        new String[]{"Вы не заполнили поле!"},
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            } else if (studentService.getGroup(groupField.getText()) == null) {
                JOptionPane.showMessageDialog(MainFrameListener.this,
                        new String[]{"Такой группы не существует!"},
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                studentService.deleteGroup(groupField.getText());
                updateGroupsButtons(groupsButtons, studentsList);
            }
        });
        groupModification.add(addGroupButton);
        groupModification.add(deleteGroupButton);
    }

    private boolean checkGroup(String newGroup) {
        for (Group groups : studentService.getAllGroups()) {
            if (newGroup.equals(groups.getGroupNumber())) {
                return false;
            }
        }
        return true;
    }

    private void updateGroupsButtons(Container groupsButtons, Container studentsList) {
        groupsButtons.removeAll();
        List<Group> allGroups = studentService.getAllGroups();
        ButtonGroup buttonGroup = new ButtonGroup();
        for (Group group : allGroups) {
            addGroupButton(group.getGroupNumber(), groupsButtons, studentsList, buttonGroup);
        }
        groupsButtons.repaint();
        groupsButtons.revalidate();
    }

    private void createPanelForGetStudentsByDate(Container studentsByDate, Container studentsList) {
        JDatePickerImpl datePicker = createDatePicker();
        JButton getStudentsByDateButton = new JButton("Поиск");
        getStudentsByDateButton.addActionListener(e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            DateLecture date = new DateLecture();
            if (datePicker.getModel().getValue() != null) {
                date.setDate(sdf.format(datePicker.getModel().getValue()));
                List<Student> studentList = studentService.getStudentsByDate(date);
                String[] studentsName = new String[studentList.size()];
                int i = 0;
                for (Student student : studentList) {
                    Group group = studentService.getGroupById(student.getGroupId());
                    studentsName[i] = student.getStudentLastname() + " " + student.getStudentName() + " " + student.getStudentPatronymic() + " " + group.getGroupNumber();
                    i++;
                }
                JList<String> students = new JList<>(studentsName);
                JScrollPane scrollPane = new JScrollPane(students);
                scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                scrollPane.setPreferredSize(new Dimension(600, 600));
                scrollPane.setMaximumSize(scrollPane.getSize());
                scrollPane.setMinimumSize(scrollPane.getSize());
                studentsList.removeAll();
                studentsList.add(scrollPane);
                studentsList.repaint();
                studentsList.revalidate();
            } else {
                JOptionPane.showMessageDialog(MainFrameListener.this,
                        new String[]{"Вы не выбрали дату!"},
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
        studentsByDate.add(datePicker);
        studentsByDate.add(getStudentsByDateButton);
    }

    private void createPanelForAddDate(Container dateOfVisit) {
        JDatePickerImpl datePicker = createDatePicker();
        dateOfVisit.add(datePicker);
        JButton addDateButton = new JButton("Добавить");
        addDateButton.addActionListener(e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            DateLecture date = new DateLecture();
            if (datePicker.getModel().getValue() != null) {
                date.setDate(sdf.format(datePicker.getModel().getValue()));
                studentService.addDate(date);
            } else {
                JOptionPane.showMessageDialog(MainFrameListener.this,
                        new String[]{"Вы не выбрали дату!"},
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
        dateOfVisit.add(addDateButton);
    }

    private JDatePickerImpl createDatePicker() {
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        return new JDatePickerImpl(datePanel, new DateLabelFormatter());
    }

    private void addGroupButton(String caption, Container containerButton, Container containerStudents, ButtonGroup buttonGroup) {
        JButton button = new JButton(caption);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setAlignmentY(Component.TOP_ALIGNMENT);
        button.addActionListener(e -> {
            Group group = studentService.getGroup(button.getText());
            createStudentsPane(containerStudents, group);
            currentGroup = group;
        });

        buttonGroup.add(button);
        containerButton.add(button);
    }

    public void createButtonsForGroups(Container containerButton, Container containerStudents) {
        containerButton.setLayout(new BoxLayout(containerButton, BoxLayout.X_AXIS));
        List<Group> allGroups = studentService.getAllGroups();
        ButtonGroup buttonGroup = new ButtonGroup();
        for (Group group : allGroups) {
            addGroupButton(group.getGroupNumber(), containerButton, containerStudents, buttonGroup);
        }
    }

    public void createStudentsPane(Container container, Group group) {
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
                    Student student = checkFullStudentName(studentName);
                    if ((Boolean) this.getValueAt(row, col)) {
                        Visit visit = new Visit();
                        studentService.addVisit(date, student, visit);
                    } else {
                        DateLecture deleteDate = new DateLecture();
                        if (student == null) {
                            JOptionPane.showMessageDialog(MainFrameListener.this,
                                    new String[]{"Такого студента не существует!"},
                                    "Warning",
                                    JOptionPane.WARNING_MESSAGE);
                        } else {
                            for (Student allStudent : studentService.getStudentsByGroup(group)) {
                                if (allStudent.getStudentLastname().equals(student.getStudentLastname())) {
                                    student.setId(allStudent.getId());
                                }
                            }
                            for (DateLecture allDate : studentService.getAllDates()) {
                                if (allDate.getDate().equals(date)) {
                                    deleteDate.setId(allDate.getId());
                                    studentService.deleteVisit(deleteDate.getId(), student.getId());
                                }
                            }
                        }
                    }
                }
            }
        };
        studentsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        studentsTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        for (int i = 1; i < studentsTable.getColumnCount(); i++) {
            studentsTable.getColumnModel().getColumn(i).setPreferredWidth(80);
        }
        studentsTable.setPreferredScrollableViewportSize(new Dimension(600, 700));
        studentsTable.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(studentsTable);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(600, 700));
        scrollPane.setMaximumSize(scrollPane.getSize());
        scrollPane.setMinimumSize(scrollPane.getSize());
        container.removeAll();
        container.add(scrollPane);
        container.repaint();
        container.revalidate();
    }

    private String[] createListOfDates() {
        List<DateLecture> dates = studentService.getAllDates();
        String[] datesOfVisit = new String[dates.size()];
        int j = 0;
        for (DateLecture date : dates) {
            datesOfVisit[j] = date.getDate();
            j++;
        }
        return datesOfVisit;
    }

    private String[] createStudentsList(Group group) {
        List<Student> studentList = studentService.getStudentsByGroup(group);
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

    private String[] createHeaderTable() {
        String[] dates = createListOfDates();
        String[] header = new String[dates.length + 1];
        header[0] = "ФИО";
        System.arraycopy(dates, 0, header, 1, header.length - 1);
        return header;
    }

    private Object[][] createTableData(Group group) {
        String[] students = createStudentsList(group);
        List<Student> studentsDb = studentService.getStudentsByGroup(group);
        String[] dates = createListOfDates();
        List<DateLecture> datesDb = studentService.getAllDates();
        Object[][] tableData = new Object[students.length][datesDb.size() + 1];
        int i = 0;
        for (Student student : studentsDb) {
            List<DateLecture> visits = studentService.getDatesByStudent(student);
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

    private boolean checkDate(List<DateLecture> datesDb, String dateString) {
        for (DateLecture date : datesDb) {
            if (date.getDate().equals(dateString)) return true;
        }
        return false;
    }
}
