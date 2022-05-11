package com.coursework.dao;

import com.coursework.dao.db.DBManager;
import com.coursework.model.DateLecture;
import com.coursework.model.Group;
import com.coursework.model.Student;
import com.coursework.model.Visit;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The layer between the database and the application
 * @author Kuzmitskaya Maryia
 * @version 1.0
 */
public class StudentDaoImpl implements StudentDao {

    private static volatile StudentDaoImpl instance;
    private static final String INSERT_DATE = "insert into dates (date_of_visit) values (?)";
    private static final String INSERT_STUDENT = "insert into students (group_id, student_name, student_lastname,student_patronymic) values ((select id from groups_students where group_number = ?),?,?,?)";
    private static final String SELECT_STUDENTS_BY_DATE = "select * from students inner join student_date on students.id = student_date.student_id inner join dates on student_date.date_id = dates.id where (select id from dates where date_of_visit = ?) = student_date.date_id and group_id = (select id from groups_students where group_number = ?)";
    private static final String INSERT_GROUP = "insert into groups_students (group_number) values (?)";
    private static final String DELETE_GROUP1 = "delete from students where group_id = (select id from groups_students where group_number = ?)";
    private static final String DELETE_GROUP2 = "delete from groups_students where group_number = ?";
    private static final String DELETE_GROUP3 = "delete from student_date where student_id = (select id from students where group_id = (select id from groups_students where group_number = ?))";
    private static final String INSERT_VISIT = "insert into student_date(date_id, student_id) values ((select id from dates where date_of_visit = ?),(select id from students where student_name = ? and student_lastname = ? and student_patronymic = ?))";
    private static final String SELECT_GROUP_BY_NUMBER = "select * from groups_students where group_number = ?";
    private static final String SELECT_GROUP_BY_ID = "select * from groups_students where id = ?";
    private static final String SELECT_ALL_GROUPS = "select * from groups_students";
    private static final String SELECT_STUDENTS_BY_GROUP = "select * from students where group_id = ?";
    private static final String SELECT_ALL_DATES = "select * from dates";
    private static final String SELECT_DATES_BY_STUDENT = "select dates.id, dates.date_of_visit from dates inner join student_date on dates.id = student_date.date_id where (select id from students where student_name = ? and student_lastname = ? and student_patronymic = ?) = student_date.student_id";
    private static final String DELETE_VISIT = "delete from student_date where date_id = ? and student_id = ?";
    private static final String SELECT_STUDENT_BY_NAME = "select * from students where student_name = ? and student_lastname = ? and student_patronymic = ? and group_id = ?";
    private static final String DELETE_STUDENT2 = "delete from students where group_id = ? and student_name = ? and student_lastname = ? and student_patronymic = ?";
    private static final String DELETE_STUDENT1 = "delete from student_date where student_id = ?";

    private StudentDaoImpl() {
    }

    /**
     * @return instance of the class
     */
    public static StudentDaoImpl getInstance() {
        if (instance == null) {
            synchronized (StudentDaoImpl.class) {
                if (instance == null) {
                    instance = new StudentDaoImpl();
                }
            }
        }
        return instance;

    }

    /**
     * Method for initializing group using a database
     * @param resultSet resultSet
     * @return group
     * @throws SQLException
     */
    public Group initGroup(ResultSet resultSet) throws SQLException {
        Group group = new Group();
        group.setId(resultSet.getInt("id"));
        group.setGroupNumber(resultSet.getString("group_number"));
        return group;
    }

    /**
     * Method for initializing student using a database
     * @param resultSet resultSet
     * @return student
     * @throws SQLException
     */
    public Student initStudent(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        student.setId(resultSet.getInt("id"));
        student.setGroupId(resultSet.getInt("group_id"));
        student.setStudentName(resultSet.getString("student_name"));
        student.setStudentLastname(resultSet.getString("student_lastname"));
        student.setStudentPatronymic(resultSet.getString("student_patronymic"));
        return student;
    }

    /**
     * Method for initializing date using a database
     * @param resultSet resultSet
     * @return date of lecture
     * @throws SQLException
     */
    public DateLecture initDates(ResultSet resultSet) throws SQLException {
        DateLecture dates = new DateLecture();
        dates.setId(resultSet.getInt("id"));
        dates.setDate(resultSet.getString("date_of_visit"));
        return dates;
    }

    /**
     * Method for initializing visit using a database
     * @param resultSet resultSet
     * @return visit
     * @throws SQLException
     */
    public Visit initVisit(ResultSet resultSet) throws SQLException {
        Visit visit = new Visit();
        visit.setId(resultSet.getInt("id"));
        visit.setDateId(resultSet.getInt("date_of_visit"));
        visit.setStudentId(resultSet.getInt("date_of_visit"));
        return visit;
    }

    /**
     * Adding a date to the database
     * @param date date
     */
    @Override
    public void addDate(DateLecture date) {
        try (Connection connection = DBManager.getConnection()) {
            Date utilDate = parseDate(date.getDate());
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DATE);
            preparedStatement.setDate(1, sqlDate);
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    date.setId(resultSet.getInt("id"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Incorrectly entered data");
            ;
        }
    }

    /**
     * Adding a student to the database
     * @param student student
     * @param group group
     */
    @Override
    public void addStudent(Student student, Group group) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STUDENT);
            preparedStatement.setString(1, group.getGroupNumber());
            preparedStatement.setString(2, student.getStudentName());
            preparedStatement.setString(3, student.getStudentLastname());
            preparedStatement.setString(4, student.getStudentPatronymic());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    student.setId(resultSet.getInt("id"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Incorrectly entered data");
            ;
        }
    }

    /**
     * Method for searching students by date
     * @param date date
     * @param group group
     * @return list of students
     */
    @Override
    public List<Student> getStudentsByDate(DateLecture date, Group group) {
        List<Student> students = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()) {
            Date utilDate = parseDate(date.getDate());
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STUDENTS_BY_DATE);
            preparedStatement.setDate(1, sqlDate);
            preparedStatement.setString(2, group.getGroupNumber());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                students.add(initStudent(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("error");
        }
        return students;
    }

    /**
     * Adding a group to the database
     * @param group group
     */
    @Override
    public void addGroup(Group group) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_GROUP);
            preparedStatement.setString(1, group.getGroupNumber());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    group.setId(resultSet.getInt("id"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Incorrectly entered data");
            ;
        }
    }

    /**
     * Delete a group to the database
     * @param group group
     */
    @Override
    public void deleteGroup(String group) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement1 = connection.prepareStatement(DELETE_GROUP1);
            preparedStatement1.setString(1, group);
            preparedStatement1.executeUpdate();
            PreparedStatement preparedStatement2 = connection.prepareStatement(DELETE_GROUP2);
            preparedStatement2.setString(1, group);
            preparedStatement2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Incorrectly entered data");
        }
    }

    /**
     * Method for searching group by number
     * @param group group
     * @return group
     */
    @Override
    public Group getGroup(String group) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_GROUP_BY_NUMBER);
            preparedStatement.setString(1, group);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return initGroup(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method for searching group by id
     * @param groupId group id
     * @return group
     */
    @Override
    public Group getGroupById(int groupId) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_GROUP_BY_ID);
            preparedStatement.setInt(1, groupId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return initGroup(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Adding a visit to the database
     * @param date date
     * @param student student
     * @param visit visit
     */
    @Override
    public void addVisit(String date, Student student, Visit visit) {
        try (Connection connection = DBManager.getConnection()) {
            Date utilDate = parseDate(date);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_VISIT);
            preparedStatement.setDate(1, sqlDate);
            preparedStatement.setString(2, student.getStudentName());
            preparedStatement.setString(3, student.getStudentLastname());
            preparedStatement.setString(4, student.getStudentPatronymic());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    visit.setId(resultSet.getInt("id"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Incorrectly entered data");
        }
    }

    /**
     * Method for parsing the date
     * @param object date
     * @return date
     */
    @Override
    public Date parseDate(String object) {
        try {
            Date date;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            format.setLenient(false);
            date = format.parse(object);
            return date;
        } catch (Exception exception) {
            System.out.println("DateLecture entered incorrectly");
            return null;
        }
    }

    /**
     * Method for searching all groups
     * @return list of groups
     */
    @Override
    public List<Group> getAllGroups() {
        List<Group> groups = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_GROUPS);
            while (resultSet.next()) {
                groups.add(initGroup(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groups;
    }

    /**
     * Method for searching students by group
     * @param group group
     * @return list of students
     */
    @Override
    public List<Student> getStudentsByGroup(Group group) {
        List<Student> students = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STUDENTS_BY_GROUP);
            preparedStatement.setInt(1, group.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                students.add(initStudent(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    /**
     * Method for searching all dates
     * @return list of dates
     */
    @Override
    public List<DateLecture> getAllDates() {
        List<DateLecture> dates = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_DATES);
            while (resultSet.next()) {
                dates.add(initDates(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dates;
    }

    /**
     * A method for finding the dates of student visits
     * @param student student
     * @return list of dates
     */
    @Override
    public List<DateLecture> getDatesByStudent(Student student) {
        List<DateLecture> dates = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_DATES_BY_STUDENT);
            preparedStatement.setString(1, student.getStudentName());
            preparedStatement.setString(2, student.getStudentLastname());
            preparedStatement.setString(3, student.getStudentPatronymic());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                dates.add(initDates(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("error");
        }
        return dates;
    }

    /**
     * Method for deleting a visit
     * @param dateId date id
     * @param studentId student id
     */
    @Override
    public void deleteVisit(int dateId, int studentId) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_VISIT);
            preparedStatement.setInt(1, dateId);
            preparedStatement.setInt(2, studentId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Incorrectly entered data");
        }
    }

    /**
     * Method for deleting a student
     * @param student student
     */
    @Override
    public void deleteStudent(Student student) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STUDENT1);
            preparedStatement.setInt(1, student.getId());
            preparedStatement.executeUpdate();
            PreparedStatement preparedStatement1 = connection.prepareStatement(DELETE_STUDENT2);
            preparedStatement1.setInt(1, student.getGroupId());
            preparedStatement1.setString(2, student.getStudentName());
            preparedStatement1.setString(3, student.getStudentLastname());
            preparedStatement1.setString(4, student.getStudentPatronymic());
            preparedStatement1.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Incorrectly entered data");
        }
    }

    /**
     * Method for searching for a student by name
     * @param student student
     * @return student
     */
    @Override
    public Student getStudentByName(Student student) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STUDENT_BY_NAME);
            preparedStatement.setString(1, student.getStudentName());
            preparedStatement.setString(2, student.getStudentLastname());
            preparedStatement.setString(3, student.getStudentPatronymic());
            preparedStatement.setInt(4, student.getGroupId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return initStudent(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
