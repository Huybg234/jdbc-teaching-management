package repository;

import constant.DatabaseConstant;
import entity.Subject;
import entity.Teacher;
import teachingtimesheet.Teaching;
import teachingtimesheet.TeachingTimeSheet;
import util.CollectionUtil;
import util.DatabaseConnection;
import util.ObjectUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String query = "select tc.id teacher_id, tc.name teacher_name, tc.address, tc.phone_number, tc.teacher_level, s.id subject_id, s.name subject_name, s.total_lesson, s.theory_lesson, s.theory_expense, tt.class_number " +
                    "from " + TEACHING_TABLE_NAME + " tt join " + TeacherDao.TEACHER_TABLE_NAME + " tc on tt.teacher_id = tc.id join " + SubjectDao.SUBJECT_TABLE_NAME + " s on tt.subject_id = s.id";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            teachings = new ArrayList<>();
            while (resultSet.next()) {
                int teacher_id = resultSet.getInt(TEACHER_ID);
                String teacher_name = resultSet.getString("teacher_name");
                String address = resultSet.getString(TeacherDao.ADDRESS);
                String phone_number = resultSet.getString(TeacherDao.PHONE_NUMBER);
                String teacher_level = resultSet.getString(TeacherDao.TEACHER_LEVEL);
                Teacher teacher = new Teacher(teacher_id, teacher_name, address, phone_number, teacher_level);

                int subject_id = resultSet.getInt(SUBJECT_ID);
                String subject_name = resultSet.getString("subject_name");
                int total_lesson = resultSet.getInt(SubjectDao.TOTAL_LESSON);
                int theory_lesson = resultSet.getInt(SubjectDao.THEORY_LESSON);
                float theory_expense = resultSet.getFloat(SubjectDao.THEORY_EXPENSE);
                Subject subject = new Subject(subject_id, subject_name, total_lesson, theory_lesson, theory_expense);

                int class_number = resultSet.getInt(CLASS_NUMBER);

                TeachingTimeSheet teachingTimeSheet = new TeachingTimeSheet(subject, class_number);
                Teaching tempTeaching = searchTeaching(teachings, teacher_id);

                if (ObjectUtil.isEmpty(tempTeaching)) {
                    Teaching teaching = new Teaching(teacher, Arrays.asList(teachingTimeSheet));
                    teachings.add(teaching);
                } else {
                    int index = teachings.indexOf(tempTeaching);
                    teachings.get(index).getTeachingSubjectClass().add(teachingTimeSheet);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(resultSet, preparedStatement, null);
        }
        return teachings;
    }

    private Teaching searchTeaching(List<Teaching> teachings, int teacherId) {

        List<Teaching> collect = teachings.stream().filter(teaching -> teaching.getTeacher().getId() == teacherId).collect(Collectors.toList());
        if (!CollectionUtil.isEmpty(collect)) {
            collect.get(0);
        }
        return null;
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
