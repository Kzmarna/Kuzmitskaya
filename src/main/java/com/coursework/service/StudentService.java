package com.coursework.service;

import com.coursework.model.Dates;
import com.coursework.model.Groups;
import com.coursework.model.Student;
import com.coursework.model.Visit;

import java.util.List;

public interface StudentService {
    Groups getGroup(String group);
    void addDate(Dates date);
    void addStudent(Student student, Groups group);
    List<Student> getStudentsByDate(Dates date);
    void addGroup(Groups group);
    void deleteGroup(String group);
    void addVisit(String date, Student student, Visit visit);
    List<Groups> getAllGroups();
    List<Student> getStudentsByGroup(Groups group);
    List<Dates> getAllDates();
    List<Dates> getDatesByStudent(Student student);
    void deleteVisit(int dateId, int studentId);
}
