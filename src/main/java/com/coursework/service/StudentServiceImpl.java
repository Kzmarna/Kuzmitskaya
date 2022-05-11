package com.coursework.service;

import com.coursework.dao.StudentDao;
import com.coursework.dao.StudentDaoImpl;
import com.coursework.model.DateLecture;
import com.coursework.model.Group;
import com.coursework.model.Student;
import com.coursework.model.Visit;

import java.util.List;

/**
 * Students service
 */
public class StudentServiceImpl implements StudentService{
    private final StudentDao studentDao = StudentDaoImpl.getInstance();

    private static volatile StudentServiceImpl studentService;

    private StudentServiceImpl(){}

    /**
     * @return instance of the class
     */
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

    /**
     * Method for searching group by number
     * @param group group
     * @return group
     */
    @Override
    public Group getGroup(String group) {
        return studentDao.getGroup(group);
    }

    /**
     * Adding a date
     * @param date date
     */
    @Override
    public void addDate(DateLecture date) {
        studentDao.addDate(date);
    }

    /**
     * Adding a student
     * @param student student
     * @param group group
     */
    @Override
    public void addStudent(Student student, Group group) {
        studentDao.addStudent(student, group);
    }

    /**
     * Method for searching students by date
     * @param date date
     * @param group group
     * @return list of students
     */
    @Override
    public List<Student> getStudentsByDate(DateLecture date, Group group) {
        return studentDao.getStudentsByDate(date, group);
    }

    /**
     * Adding a group
     * @param group group
     */
    @Override
    public void addGroup(Group group) {
        studentDao.addGroup(group);
    }

    /**
     *  Delete a group
     * @param group group
     */
    @Override
    public void deleteGroup(String group) {
        studentDao.deleteGroup(group);
    }

    /**
     * Adding a visit
     * @param date date
     * @param student student
     * @param visit visit
     */
    @Override
    public void addVisit(String date, Student student, Visit visit) {
        studentDao.addVisit(date, student, visit);
    }

    /**
     * Method for searching all groups
     * @return list of groups
     */
    @Override
    public List<Group> getAllGroups() {
        return studentDao.getAllGroups();
    }

    /**
     * Method for searching students by group
     * @param group group
     * @return list of students
     */
    @Override
    public List<Student> getStudentsByGroup(Group group) {
        return studentDao.getStudentsByGroup(group);
    }

    /**
     * Method for searching all dates
     * @return list of dates
     */
    @Override
    public List<DateLecture> getAllDates() {
        return studentDao.getAllDates();
    }

    /**
     * A method for finding the dates of student visits
     * @param student student
     * @return list of date
     */
    @Override
    public List<DateLecture> getDatesByStudent(Student student) {
        return studentDao.getDatesByStudent(student);
    }

    /**
     * Method for deleting a visit
     * @param dateId date id
     * @param studentId student id
     */
    @Override
    public void deleteVisit(int dateId, int studentId) {
        studentDao.deleteVisit(dateId, studentId);
    }

    /**
     * Method for searching group by id
     * @param groupId group id
     * @return group
     */
    @Override
    public Group getGroupById(int groupId) {
        return studentDao.getGroupById(groupId);
    }

    /**
     * Method for deleting a student
     * @param student student
     */
    @Override
    public void deleteStudent(Student student) {
        studentDao.deleteStudent(student);
    }

    /**
     * Method for searching for a student by name
     * @param student student
     * @return student
     */
    @Override
    public Student getStudentByName(Student student) {
        return studentDao.getStudentByName(student);
    }
}
