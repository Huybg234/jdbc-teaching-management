package repository;

import constant.DatabaseConstant;
import entity.Teacher;
import util.CollectionUtil;
import util.DatabaseConnection;
import util.ObjectUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherDao {

    public static final String TEACHER_TABLE_NAME = "teacher";

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String ADDRESS = "address";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String TEACHER_LEVEL = "teacher_level";

    private static final Connection connection;

    static {
        connection = DatabaseConnection.openConnection(DatabaseConstant.DRIVER_STRING, DatabaseConstant.URL, DatabaseConstant.USERNAME, DatabaseConstant.PASSWORD);
    }

    public List<Teacher> getTeachers() {
        List<Teacher> teachers = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM " + TEACHER_TABLE_NAME + " order by id";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            teachers = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(ID);
                String name = resultSet.getString(NAME);
                String address = resultSet.getString(ADDRESS);
                String phone_number = resultSet.getString(PHONE_NUMBER);
                String teacher_level = resultSet.getString(TEACHER_LEVEL);
                Teacher teacher = new Teacher(id, name, address, phone_number, teacher_level);
                if (ObjectUtil.isEmpty(teacher)) {
                    continue;
                }
                teachers.add(teacher);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(resultSet, preparedStatement, null);
        }
        return teachers;
    }

    public void insertNewTeacher(Teacher teacher) {
        if (ObjectUtil.isEmpty(teacher)) {
            return;
        }
        PreparedStatement preparedStatement = null;
        try {
            String query = "INSERT INTO " + TEACHER_TABLE_NAME + " VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, teacher.getId());
            preparedStatement.setString(2, teacher.getName());
            preparedStatement.setString(3, teacher.getAddress());
            preparedStatement.setString(4, teacher.getPhoneNumber());
            preparedStatement.setString(5, teacher.getLevel());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(null, preparedStatement, null);
        }
    }

    public void insertNewTeacher(List<Teacher> teachers) {
        if (CollectionUtil.isEmpty(teachers)) {
            return;
        }
        teachers.forEach(this::insertNewTeacher);
    }

}
