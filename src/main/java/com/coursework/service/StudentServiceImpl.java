package com.coursework.service;

import com.coursework.dao.StudentDao;
import com.coursework.dao.StudentDaoImpl;
import com.coursework.model.Dates;
import com.coursework.model.Groups;
import com.coursework.model.Student;
import com.coursework.model.Visit;

import java.util.List;

public class StudentServiceImpl implements StudentService{
    private final StudentDao studentDao = StudentDaoImpl.getInstance();

    private static volatile StudentServiceImpl studentService;

    private StudentServiceImpl(){}

    public static StudentServiceImpl getInstance() {
        if (studentService == null) {
            synchronized (StudentServiceImpl.class) {
                if (studentService == null) {
                    studentService = new StudentServiceImpl();
                }
            }

        }
        return studentService;
    }

    @Override
    public Groups getGroup(String group) {
        return studentDao.getGroup(group);
    }

    @Override
    public void addDate(Dates date) {
        studentDao.addDate(date);
    }

    @Override
    public void addStudent(Student student, Groups group) {
        studentDao.addStudent(student, group);
    }

    @Override
    public List<Student> getStudentsByDate(Dates date) {
        return studentDao.getStudentsByDate(date);
    }

    @Override
    public void addGroup(Groups group) {
        studentDao.addGroup(group);
    }

    @Override
    public void deleteGroup(String group) {
        studentDao.deleteGroup(group);
    }

    @Override
    public void addVisit(String date, Student student, Visit visit) {
        studentDao.addVisit(date, student, visit);
    }

    @Override
    public List<Groups> getAllGroups() {
        return studentDao.getAllGroups();
    }

    @Override
    public List<Student> getStudentsByGroup(Groups group) {
        return studentDao.getStudentsByGroup(group);
    }

    @Override
    public List<Dates> getAllDates() {
        return studentDao.getAllDates();
    }

    @Override
    public List<Dates> getDatesByStudent(Student student) {
        return studentDao.getDatesByStudent(student);
    }

    @Override
    public void deleteVisit(int dateId, int studentId) {
        studentDao.deleteVisit(dateId, studentId);
    }
}
