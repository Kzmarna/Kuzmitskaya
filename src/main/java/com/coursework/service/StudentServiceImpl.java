package com.coursework.service;

import com.coursework.dao.StudentDao;
import com.coursework.dao.StudentDaoImpl;
import com.coursework.model.DateLecture;
import com.coursework.model.Group;
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
    public Group getGroup(String group) {
        return studentDao.getGroup(group);
    }

    @Override
    public void addDate(DateLecture date) {
        studentDao.addDate(date);
    }

    @Override
    public void addStudent(Student student, Group group) {
        studentDao.addStudent(student, group);
    }

    @Override
    public List<Student> getStudentsByDate(DateLecture date) {
        return studentDao.getStudentsByDate(date);
    }

    @Override
    public void addGroup(Group group) {
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
    public List<Group> getAllGroups() {
        return studentDao.getAllGroups();
    }

    @Override
    public List<Student> getStudentsByGroup(Group group) {
        return studentDao.getStudentsByGroup(group);
    }

    @Override
    public List<DateLecture> getAllDates() {
        return studentDao.getAllDates();
    }

    @Override
    public List<DateLecture> getDatesByStudent(Student student) {
        return studentDao.getDatesByStudent(student);
    }

    @Override
    public void deleteVisit(int dateId, int studentId) {
        studentDao.deleteVisit(dateId, studentId);
    }

    @Override
    public Group getGroupById(int groupId) {
        return studentDao.getGroupById(groupId);
    }

    @Override
    public void deleteStudent(Student student) {
        studentDao.deleteStudent(student);
    }

    @Override
    public Student getStudentByName(Student student) {
        return studentDao.getStudentByName(student);
    }
}
