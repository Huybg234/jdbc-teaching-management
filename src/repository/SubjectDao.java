package repository;

import constant.DatabaseConstant;
import entity.Subject;
import util.CollectionUtil;
import util.DatabaseConnection;
import util.ObjectUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectDao {

    public static final String SUBJECT_TABLE_NAME = "subject";

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String TOTAL_LESSON = "total_lesson";
    public static final String THEORY_LESSON = "theory_lesson";
    public static final String THEORY_EXPENSE = "theory_expense";

    private static final Connection connection;

    static {
        connection = DatabaseConnection.openConnection(DatabaseConstant.DRIVER_STRING, DatabaseConstant.URL, DatabaseConstant.USERNAME, DatabaseConstant.PASSWORD);
    }

    public List<Subject> getSubjects() {
        List<Subject> subjects = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM " + SUBJECT_TABLE_NAME;
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            subjects = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(ID);
                String name = resultSet.getString(NAME);
                int totalLesson = resultSet.getInt(TOTAL_LESSON);
                int theoryLesson = resultSet.getInt(THEORY_LESSON);
                float theoryExpense = resultSet.getFloat(THEORY_EXPENSE);
                Subject subject = new Subject(id, name, totalLesson, theoryLesson, theoryExpense);
                if (ObjectUtil.isEmpty(subject)) {
                    continue;
                }
                subjects.add(subject);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(resultSet, preparedStatement, null);
        }
        return subjects;
    }

    public void insertNewSubject(Subject subject) {
        if (ObjectUtil.isEmpty(subject)) {
            return;
        }
        PreparedStatement preparedStatement = null;
        try {
            String query = "INSERT INTO " + SUBJECT_TABLE_NAME + " VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, subject.getId());
            preparedStatement.setString(2, subject.getName());
            preparedStatement.setInt(3, subject.getTotalLesson());
            preparedStatement.setInt(4, subject.getTheoryLesson());
            preparedStatement.setFloat(5, subject.getUnitTheoryCost());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(null, preparedStatement, null);
        }
    }

    public void insertNewSubject(List<Subject> subjects) {
        if (!CollectionUtil.isEmpty(subjects)) {
            return;
        }
        subjects.forEach(this::insertNewSubject);
    }

}
