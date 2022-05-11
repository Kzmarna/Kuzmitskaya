package com.coursework.service;

import com.coursework.model.DateLecture;
import com.coursework.model.Group;
import com.coursework.model.Student;
import com.coursework.model.Visit;

import java.util.List;

public interface StudentService {
    Group getGroup(String group);
    void addDate(DateLecture date);
    void addStudent(Student student, Group group);
    List<Student> getStudentsByDate(DateLecture date, Group group);
    void addGroup(Group group);
    void deleteGroup(String group);
    void addVisit(String date, Student student, Visit visit);
    List<Group> getAllGroups();
    List<Student> getStudentsByGroup(Group group);
    List<DateLecture> getAllDates();
    List<DateLecture> getDatesByStudent(Student student);
    void deleteVisit(int dateId, int studentId);
    Group getGroupById(int groupId);
    void deleteStudent(Student student);
    Student getStudentByName(Student student);
}
