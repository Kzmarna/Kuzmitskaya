package com.coursework.service;

import com.coursework.model.DateLecture;
import com.coursework.model.Student;
import com.spire.doc.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileServiceImpl implements FileService{

    private final StudentService studentService;
    private static volatile FileServiceImpl fileService;

    private FileServiceImpl(){
        studentService = StudentServiceImpl.getInstance();
    }

    public static FileServiceImpl getInstance() {
        if (fileService == null) {
            synchronized (FileServiceImpl.class) {
                if (fileService == null) {
                    fileService = new FileServiceImpl();
                }
            }

        }
        return fileService;
    }
    @Override
    public File createFile(Container container) {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.showSaveDialog(container);

        return new File(jFileChooser.getSelectedFile().getPath() + ".docx");
    }

    @Override
    public void writeDataToWord(File file, List<Student> students) {
        Document document = new Document();
        Section section = document.addSection();
        List<DateLecture> dates = studentService.getAllDates();
        Table table = section.addTable(true);
        table.resetCells(students.size() + 1, dates.size() + 1);
        createHeader(table, dates);
        createData(table, students, dates);
        document.saveToFile(file.getPath(), FileFormat.Docx);
    }

    private void createHeader(Table table, List<DateLecture> dates) {
        List<String> headerStrings = new ArrayList<>(dates.size() + 1);
        headerStrings.add("ФИО");
        dates.forEach(date -> headerStrings.add(date.getDate()));
        TableRow row = table.getFirstRow();
        row.isHeader(true);
        for (int i = 0; i < headerStrings.size(); i++) {
            row.getCells().get(i).addParagraph().appendText(headerStrings.get(i));
        }
    }

    private void createData(Table table, List<Student> students, List<DateLecture> dates) {
        for (int i = 0; i < students.size(); i++) {
            TableRow row = table.getRows().get(i + 1);
            row.getCells().get(0).addParagraph().appendText(students.get(i).getFullName());
            List<DateLecture> visits = studentService.getDatesByStudent(students.get(i));
            for (int j = 1; j < row.getCells().getCount(); j++) {
                if (checkDate(visits, dates.get(j - 1).getDate())) {
                    row.getCells().get(j).addParagraph().appendText("+");
                } else {
                    row.getCells().get(j).addParagraph().appendText("-");
                }
            }
        }
    }

    private static boolean checkDate(List<DateLecture> datesDb, String dateString) {
        for (DateLecture date : datesDb) {
            if (date.getDate().equals(dateString)) return true;
        }
        return false;
    }
}

