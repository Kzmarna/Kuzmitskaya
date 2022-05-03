package com.coursework.dao;

import com.coursework.model.Dates;
import com.coursework.model.Groups;
import com.coursework.model.Student;
import com.coursework.model.Visit;

import java.util.Date;
import java.util.List;

public interface StudentDao {
    void addDate(Dates date);
    void addStudent(Student student, Groups group);
    List<Student> getStudentsByDate(Dates date);
    void addGroup(Groups group);
    void deleteGroup(String group);
    Groups getGroup(String group);
    void addVisit(String date, Student student, Visit visit);
    Date parseDate(String date);
    List<Groups> getAllGroups();
    List<Student> getStudentsByGroup(Groups group);
    List<Dates> getAllDates();
    List<Dates> getDatesByStudent(Student student);
    void deleteVisit(int dateId, int studentId);
    Groups getGroupById(int groupId);
    void deleteStudent(Student student);
    Student getStudentByName(Student student);
}
