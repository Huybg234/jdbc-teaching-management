package main;

import entity.Subject;
import entity.Teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TeacherCreator {

    public void createNewTeacher() {
        System.out.println("Nhập số lượng giáo viên cần thêm mới: ");
        int teacherQuantity = 0;
        boolean isValidTeacherQuantity = true;
        do {
            try {
                teacherQuantity = new Scanner(System.in).nextInt();
                isValidTeacherQuantity = true;
            } catch (Exception e) {
                System.out.println("Không được nhập ký tự khác ngoài số! Nhập lại: ");
                isValidTeacherQuantity = false;
                continue;
            }
            if (teacherQuantity <= 0) {
                System.out.print("Số lượng giáo viên phải lớn hơn 0! Nhập lại: ");
                isValidTeacherQuantity = false;
            }
        } while (!isValidTeacherQuantity);

        List<Teacher> tempTeachers = new ArrayList<>();
        for (int i = 0; i < teacherQuantity; i++) {
            Teacher teacher = new Teacher();
            teacher.inputInfo();
            tempTeachers.add(teacher);
        }
        MainRun.teachers.addAll(tempTeachers);
        MainRun.teacherDao.insertNewTeacher(tempTeachers);
    }

}
