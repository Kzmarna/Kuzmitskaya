package com.coursework.dao;

import com.coursework.dao.db.DBManager;
import com.coursework.model.Dates;
import com.coursework.model.Groups;
import com.coursework.model.Student;
import com.coursework.model.Visit;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentDaoImpl implements StudentDao {

    private static volatile StudentDaoImpl instance;
    private static final String INSERT_DATE = "insert into dates (date_of_visit) values (?)";
    private static final String INSERT_STUDENT = "insert into students (group_id, student_name, student_lastname,student_patronymic) values ((select id from groups_students where group_number = ?),?,?,?)";
    private static final String SELECT_STUDENTS_BY_DATE = "select * from students inner join student_date on students.id = student_date.student_id inner join dates on student_date.date_id = dates.id where (select id from dates where date_of_visit = ?) = student_date.date_id";
    private static final String INSERT_GROUP = "insert into groups_students (group_number) values (?)";
    private static final String DELETE_GROUP1 = "delete from students where (select id from groups_students where group_number = ?) = group_id";
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
    private static final String SELECT_STUDENT_BY_NAME = "select * from students where student_name = ? and student_lastname = ? and student_patronymic = ?";
    private static final String DELETE_STUDENT2 = "delete from students where group_id = ? and student_name = ? and student_lastname = ? and student_patronymic = ?";
    private static final String DELETE_STUDENT1 = "delete from student_date where student_id = ?";

    private StudentDaoImpl() {
    }

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

    public Groups initGroup(ResultSet resultSet) throws SQLException {
        Groups group = new Groups();
        group.setId(resultSet.getInt("id"));
        group.setGroupNumber(resultSet.getString("group_number"));
        return group;
    }

    public Student initStudent(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        student.setId(resultSet.getInt("id"));
        student.setGroupId(resultSet.getInt("group_id"));
        student.setStudentName(resultSet.getString("student_name"));
        student.setStudentLastname(resultSet.getString("student_lastname"));
        student.setStudentPatronymic(resultSet.getString("student_patronymic"));
        return student;
    }

    public Dates initDates(ResultSet resultSet) throws SQLException {
        Dates dates = new Dates();
        dates.setId(resultSet.getInt("id"));
        dates.setDate(resultSet.getString("date_of_visit"));
        return dates;
    }

    @Override
    public void addDate(Dates date) {
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

    @Override
    public void addStudent(Student student, Groups group) {
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

    @Override
    public List<Student> getStudentsByDate(Dates date) {
        List<Student> students = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()) {
            Date utilDate = parseDate(date.getDate());
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STUDENTS_BY_DATE);
            preparedStatement.setDate(1, sqlDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                students.add(initStudent(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("error");
            ;
        }
        return students;
    }

    @Override
    public void addGroup(Groups group) {
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

    @Override
    public void deleteGroup(String group) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_GROUP3);
            preparedStatement.setString(1, group);
            preparedStatement.executeUpdate();
            PreparedStatement preparedStatement1 = connection.prepareStatement(DELETE_GROUP1);
            preparedStatement1.setString(1, group);
            preparedStatement1.executeUpdate();
            PreparedStatement preparedStatement2 = connection.prepareStatement(DELETE_GROUP2);
            preparedStatement2.setString(1, group);
            preparedStatement2.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Incorrectly entered data");
        }
    }

    @Override
    public Groups getGroup(String group) {
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

    @Override
    public Groups getGroupById(int groupId) {
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
            ;
        }
    }

    @Override
    public Date parseDate(String object) {
        try {
            Date date;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            format.setLenient(false);
            date = format.parse(object);
            return date;
        } catch (Exception exception) {
            System.out.println("Dates entered incorrectly");
            return null;
        }
    }

    @Override
    public List<Groups> getAllGroups() {
        List<Groups> groups = new ArrayList<>();
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

    @Override
    public List<Student> getStudentsByGroup(Groups group) {
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

    @Override
    public List<Dates> getAllDates() {
        List<Dates> dates = new ArrayList<>();
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

    @Override
    public List<Dates> getDatesByStudent(Student student) {
        List<Dates> dates = new ArrayList<>();
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

    @Override
    public Student getStudentByName(Student student) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STUDENT_BY_NAME);
            preparedStatement.setString(1, student.getStudentName());
            preparedStatement.setString(2, student.getStudentLastname());
            preparedStatement.setString(3, student.getStudentPatronymic());
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
