package com.coursework.dao;

import com.coursework.model.DateLecture;
import com.coursework.model.Group;
import com.coursework.model.Student;
import com.coursework.model.Visit;

import java.util.Date;
import java.util.List;

public interface StudentDao {
    void addDate(DateLecture date);
    void addStudent(Student student, Group group);
    List<Student> getStudentsByDate(DateLecture date, Group group);
    void addGroup(Group group);
    void deleteGroup(String group);
    Group getGroup(String group);
    void addVisit(String date, Student student, Visit visit);
    Date parseDate(String date);
    List<Group> getAllGroups();
    List<Student> getStudentsByGroup(Group group);
    List<DateLecture> getAllDates();
    List<DateLecture> getDatesByStudent(Student student);
    void deleteVisit(int dateId, int studentId);
    Group getGroupById(int groupId);
    void deleteStudent(Student student);
    Student getStudentByName(Student student);
}
