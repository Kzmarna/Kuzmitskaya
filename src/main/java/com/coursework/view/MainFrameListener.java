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
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

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
        frame.setPreferredSize(new Dimension(1100, 900));
        frame.setResizable(false);
        frame.setLocation(400, 100);

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
                try {
                    fileService.writeDataToWord(file, studentService.getStudentsByGroup(currentGroup), currentGroup.getGroupNumber());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(MainFrameListener.this,
                        new String[]{"Вы не выбрали группу!"},
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
        JButton author = new JButton("Об авторе");
        author.addActionListener(e -> {
            frame.dispose();
            new AboutAuthor();
        });
        JButton app = new JButton("О программе");
        app.addActionListener(e -> {
            frame.dispose();
            new AboutApp();
        });

        filePanel.add(fileButton);
        filePanel.add(author);
        filePanel.add(app);

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
        studentModification.add(enterStudentName);
        studentModification.add(studentsName);
        JButton addStudentButton = new JButton("Добавить");
        JButton deleteStudentButton = new JButton("Удалить");
        addStudentButton.addActionListener(e -> {
            if (studentsName.getText().isEmpty()) {
                JOptionPane.showMessageDialog(MainFrameListener.this,
                        new String[]{"Вы не заполнили поле!"},
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            } else if (checkFullStudentName(studentsName.getText()) == null) {
                JOptionPane.showMessageDialog(MainFrameListener.this,
                        new String[]{"Вы ввели некорректное ФИО студента!"},
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            } else if (currentGroup == null) {
                JOptionPane.showMessageDialog(MainFrameListener.this,
                        new String[]{"Вы не выбрали группу!"},
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                String studentName = studentsName.getText();
                Student student = checkFullStudentName(studentName);
                studentService.addStudent(student, currentGroup);
            }
        });
        deleteStudentButton.addActionListener(e ->
        {
            if (studentsName.getText().isEmpty()) {
                JOptionPane.showMessageDialog(MainFrameListener.this,
                        new String[]{"Вы не заполнили поле!"},
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            } else if (checkFullStudentName(studentsName.getText()) == null) {
                JOptionPane.showMessageDialog(MainFrameListener.this,
                        new String[]{"Вы ввели некорректное ФИО студента!"},
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            } else if (currentGroup == null) {
                JOptionPane.showMessageDialog(MainFrameListener.this,
                        new String[]{"Вы не выбрали группу!"},
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                String studentName = studentsName.getText();
                Student student = checkFullStudentName(studentName);
                if (student == null) {
                    JOptionPane.showMessageDialog(MainFrameListener.this,
                            new String[]{"Вы ввели некорректное имя!"},
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    student.setGroupId(studentService.getGroup(currentGroup.getGroupNumber()).getId());
                }
                if (studentService.getStudentByName(student) != null) {
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
                List<Student> studentList = studentService.getStudentsByDate(date, currentGroup);
                String[] studentsName = new String[studentList.size()];
                int i = 0;
                for (Student student : studentList) {
                    studentsName[i] = student.getStudentLastname() + " " + student.getStudentName() + " " + student.getStudentPatronymic();
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
            String groupNumber = button.getText();
            Group group = studentService.getGroup(groupNumber);
            try {
                createStudentsPane(containerStudents, group);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(MainFrameListener.this,
                        new String[]{"Что-то пошло не так..."},
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
            currentGroup = group;
        });
        buttonGroup.add(button);
        containerButton.add(button);
    }

    private void createButtonsForGroups(Container containerButton, Container containerStudents) {
        containerButton.setLayout(new BoxLayout(containerButton, BoxLayout.X_AXIS));
        List<Group> allGroups = studentService.getAllGroups();
        ButtonGroup buttonGroup = new ButtonGroup();
        for (Group group : allGroups) {
            addGroupButton(group.getGroupNumber(), containerButton, containerStudents, buttonGroup);
        }
    }

    private void createStudentsPane(Container container, Group group) throws ParseException {
        JTable studentsTable = new JTable(createTableData(group), createHeaderTable()) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 0 || column == 1) {
                    return String.class;
                }
                return Boolean.class;
            }

            @Override
            public void setValueAt(Object value, int row, int col) {
                super.setValueAt(value, row, col);
                if (col > 1) {
                    String studentName = (String) getValueAt(row, 0);
                    String date = null;
                    try {
                        Date dateOldFormat = new SimpleDateFormat("dd/MM/yyyy").parse(createHeaderTable()[col]);
                        date = new SimpleDateFormat("yyyy-MM-dd").format(dateOldFormat);
                    } catch (ParseException e) {
                        JOptionPane.showMessageDialog(MainFrameListener.this,
                                new String[]{"Что-то пошло не так..."},
                                "Warning",
                                JOptionPane.ERROR_MESSAGE);
                    }
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
                    container.removeAll();
                    try {
                        createStudentsPane(container, group);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    container.repaint();
                    container.revalidate();
                }
            }
        };
        studentsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        studentsTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        studentsTable.getColumnModel().getColumn(1).setPreferredWidth(110);
        for (int i = 2; i < studentsTable.getColumnCount(); i++) {
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

    private String[] createListOfDatesForHeader() throws ParseException {
        List<DateLecture> dates = studentService.getAllDates();
        String[] datesOfVisit = new String[dates.size()];
        int j = 0;
        for (DateLecture date : dates) {
            Date dateOldFormat = new SimpleDateFormat("yyyy-MM-dd").parse(date.getDate());
            date.setDate(new SimpleDateFormat("dd/MM/yyyy").format(dateOldFormat));
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

    private String[] createHeaderTable() throws ParseException {
        String[] dates = createListOfDatesForHeader();
        String[] header = new String[dates.length + 2];
        header[0] = "ФИО";
        header[1] = "Кол-во пропусков";
        System.arraycopy(dates, 0, header, 2, dates.length);
        return header;
    }

    private Object[][] createTableData(Group group) {
        String[] students = createStudentsList(group);
        List<Student> studentsDb = studentService.getStudentsByGroup(group);
        String[] dates = createListOfDates();
        List<DateLecture> datesDb = studentService.getAllDates();
        Object[][] tableData = new Object[students.length][datesDb.size() + 2];
        int i = 0;
        for (Student student : studentsDb) {
            List<DateLecture> visits = studentService.getDatesByStudent(student);
            tableData[i][0] = students[i];
            tableData[i][1] = datesDb.size() - studentService.getDatesByStudent(student).size();
            int k = 0;
            for (int j = 2; j < dates.length + 2; j++) {
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
