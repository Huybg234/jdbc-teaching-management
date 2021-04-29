package repository;

import constant.DatabaseConstant;
import teachingtimesheet.Teaching;
import util.CollectionUtil;
import util.DatabaseConnection;
import util.ObjectUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TeachingDao {

    private static final String TEACHING_TABLE_NAME = "teaching_time_sheet";

    private static final String TEACHER_ID = "teacher_id";
    private static final String SUBJECT_ID = "subject_id";
    private static final String CLASS_NUMBER = "class_number";

    private static final Connection connection;

    static {
        connection = DatabaseConnection.openConnection(DatabaseConstant.DRIVER_STRING, DatabaseConstant.URL, DatabaseConstant.USERNAME, DatabaseConstant.PASSWORD);
    }

    public List<Teaching> getTeachingTimeSheet() {
        List<Teaching> teachings = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        try {
//            String query = "SELECT * FROM " + SUBJECT_TABLE_NAME;
//            preparedStatement = connection.prepareStatement(query);
//            resultSet = preparedStatement.executeQuery();
//            subjects = new ArrayList<>();
//            while (resultSet.next()) {
//                int id = resultSet.getInt(ID);
//                String name = resultSet.getString(NAME);
//                int totalLesson = resultSet.getInt(TOTAL_LESSON);
//                int theoryLesson = resultSet.getInt(THEORY_LESSON);
//                float theoryExpense = resultSet.getFloat(THEORY_EXPENSE);
//                Subject subject = new Subject(id, name, totalLesson, theoryLesson, theoryExpense);
//                if (ObjectUtil.isEmpty(subject)) {
//                    continue;
//                }
//                subjects.add(subject);
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        } finally {
//            DatabaseConnection.closeConnection(resultSet, preparedStatement, null);
//        }
        return teachings;
    }

    public void insertNewTeachingTimeSheet(Teaching teaching) {
        if (ObjectUtil.isEmpty(teaching)) {
            return;
        }
        int teacherId = teaching.getTeacher().getId();
        teaching.getTeachingSubjectClass().forEach(timeSheet -> {
            PreparedStatement preparedStatement = null;
            try {
                String query = "INSERT INTO " + TEACHING_TABLE_NAME + " VALUES (?, ?, ?)";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, teacherId);
                preparedStatement.setInt(2, timeSheet.getSubject().getId());
                preparedStatement.setInt(3, timeSheet.getTotalClass());
                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                DatabaseConnection.closeConnection(null, preparedStatement, null);
            }
        });
    }

    public void insertNewTeachingTimeSheet(List<Teaching> teachings) {
        if (!CollectionUtil.isEmpty(teachings)) {
            return;
        }
        teachings.forEach(this::insertNewTeachingTimeSheet);
    }


}
