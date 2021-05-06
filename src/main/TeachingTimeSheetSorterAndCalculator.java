package main;

import teachingtimesheet.Teaching;
import util.CollectionUtil;

import java.util.Comparator;
import java.util.Scanner;

/**
 * Chức năng sắp xếp và tính toán này hoàn toàn có thể sử dụng sql để thực hiện mang lại hiệu suất cao hơn và thu gọn code hơn
 * Nhưng trong bài này sẽ demo cách sử dụng java 8
 */
public class TeachingTimeSheetSorterAndCalculator {

    public void sortTeachingTable() {
        if (CollectionUtil.isEmpty(MainRun.teachings)) {
            System.out.println("Nhập bảng chấm công trước khi sắp xếp");
            return;
        }
        do {
            System.out.println("------Sắp xếp danh sách chấm công---------");
            System.out.println("1.Theo tên giảng viên");
            System.out.println("2.Theo số tiết giảng dạy mỗi môn");
            System.out.println("3.Thoát");
            System.out.println("Nhập sự lựa chọn của bạn");
            int choice = 0;
            boolean checkChoice = true;
            do {
                try {
                    choice = new Scanner(System.in).nextInt();
                    checkChoice = true;
                } catch (Exception e) {
                    System.out.println("không được phép có ký tự khác ngoài ký tự số! Nhập lại");
                    checkChoice = false;
                    continue;
                }
                if (choice <= 0 || choice > 3) {
                    System.out.print("Nhập trong khoảng từ 1 đến 3! Nhập lại: ");
                    checkChoice = false;
                }
            } while (!checkChoice);
            switch (choice) {
                case 1:
                    sortByTeacherName();
                    break;
                case 2:
                    sortBySubjectLesson();
                    break;
                case 3:
                    return;
            }
        } while (true);
    }

    public void sortByTeacherName() {
        MainRun.teachings.sort(Comparator.comparing(teaching -> teaching.getTeacher().getName()));
        MainRun.printTeaching();
    }

    public void sortBySubjectLesson() {
        MainRun.teachings.forEach(teaching -> {
            int totalLesson = teaching.getTeachingSubjectClass().stream().mapToInt(teachingTimeSheet -> teachingTimeSheet.getTotalClass() * teachingTimeSheet.getSubject().getTotalLesson()).sum();
            teaching.setTotalLesson(totalLesson);
        });

        MainRun.teachings.sort(Comparator.comparingInt(Teaching::getTotalLesson));
        MainRun.printTeaching();
    }

    public void teacherIncome() {
        if (CollectionUtil.isEmpty(MainRun.teachings)) {
            System.out.println("Nhập bảng chấm công trước khi tính lương!");
            return;
        }

        MainRun.teachings.forEach(teaching -> {
            double salary = teaching.getTeachingSubjectClass().stream().mapToDouble(teachingTimeSheet -> {
                int theoryLesson = teachingTimeSheet.getSubject().getTheoryLesson();
                float unitTheoryCost = teachingTimeSheet.getSubject().getUnitTheoryCost();
                int practicalLesson = teachingTimeSheet.getSubject().getTotalLesson() - theoryLesson;

                return teachingTimeSheet.getTotalClass() * (
                        (theoryLesson + practicalLesson * 0.7) * unitTheoryCost
                );
            }).sum();
            System.out.println("Lương của giảng viên " + teaching.getTeacher().getName() + " là: " + salary);
        });
    }
}
