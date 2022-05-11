package com.coursework.service;

import com.coursework.model.DateLecture;
import com.coursework.model.Student;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class FileServiceImpl implements FileService {

    private final StudentService studentService;
    private static volatile FileServiceImpl fileService;

    private FileServiceImpl() {
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

        return new File(jFileChooser.getSelectedFile().getPath() + ".xlsx");
    }

    @Override
    public void writeDataToWord(File file, List<Student> students, String group) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet("Student Data");
        XSSFRow row;

        Map<Integer, Object[]> studentData = new TreeMap<>();
        String[] groupRow = new String[2];
        groupRow[1] = "Группа №" + group;
        studentData.put(
                1,
                groupRow
        );
        studentData.put(
                2,
                setHeader(studentService.getAllDates()));

        setTableData(studentData, students, studentService.getAllDates());
        Set<Integer> keyId = studentData.keySet();

        int rowId = 0;
        for (Integer key : keyId) {
            row = spreadsheet.createRow(rowId++);
            Object[] objectArr = studentData.get(key);
            int cellId = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellId++);
                cell.setCellValue((String) obj);
            }
        }

        spreadsheet.setColumnWidth(0, 1250);
        spreadsheet.setColumnWidth(1, 8000);
        spreadsheet.setColumnWidth(2, 4100);
        for (int i = 3; i < studentService.getAllDates().size() + 3; i++) {
            spreadsheet.setColumnWidth(i, 2700);
        }

        FileOutputStream out = new FileOutputStream(file.getPath());
        workbook.write(out);
        out.close();
    }

    private String[] setHeader(List<DateLecture> dates) {
        String[] headerStrings = new String[dates.size() + 3];
        headerStrings[0] = "№";
        headerStrings[1] = "ФИО";
        headerStrings[2] = "Кол-во пропусков";
        for (int i = 3; i < headerStrings.length; i++) {
            headerStrings[i] = dates.get(i - 3).getDate();
        }
        return headerStrings;
    }

    private void setTableData(Map<Integer, Object[]> studentData, List<Student> students1, List<DateLecture> dates) {
        String[] rowData = new String[dates.size() + 3];
        String studentsFullName;
        int numberOfPasses;
        List<Student> students = students1.stream()
                .sorted(Comparator.comparing(Student::getFullName))
                .collect(Collectors.toList());

        for (int i = 0; i < students.size(); i++) {
            List<DateLecture> visits = studentService.getDatesByStudent(students.get(i));
            studentsFullName = students.get(i).getFullName();
            numberOfPasses = dates.size() - studentService.getDatesByStudent(students.get(i)).size();
            rowData[0] = String.valueOf(i + 1);
            rowData[1] = studentsFullName;
            rowData[2] = String.valueOf(numberOfPasses);
            for (int j = 0; j < dates.size(); j++) {
                if (checkDate(visits, dates.get(j).getDate())) {
                    rowData[j + 3] = "+";
                } else {
                    rowData[j + 3] = "-";
                }
            }
            studentData.put(i + 3, rowData);
            rowData = new String[dates.size() + 3];
        }
    }

    private static boolean checkDate(List<DateLecture> datesDb, String dateString) {
        for (DateLecture date : datesDb) {
            if (date.getDate().equals(dateString)) return true;
        }
        return false;
    }
}

