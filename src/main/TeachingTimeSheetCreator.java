package main;

import entity.Subject;
import teachingtimesheet.Teaching;
import teachingtimesheet.TeachingTimeSheet;
import util.CollectionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TeachingTimeSheetCreator {

    public boolean isValidSubjectAndTeacher() {
        return !CollectionUtil.isEmpty(MainRun.subjects) && !CollectionUtil.isEmpty(MainRun.teachers);
    }

    public void createTeachingTable() {
        if (!isValidSubjectAndTeacher()) {
            System.out.println("Bạn cần nhập giảng viên và môn học trước khi thống kê! ");
            return;
        }
        for (int i = 0; i < MainRun.teachers.size(); i++) {
            System.out.println("------Kê khai cho giảng viên " + MainRun.teachers.get(i).getName() + "----------");
            System.out.print("Nhập số môn học mà giảng viên " + MainRun.teachers.get(i).getName() + " dạy :");
            int subjectNumber = 0;
            boolean check = true;
            do {
                try {
                    subjectNumber = new Scanner(System.in).nextInt();
                    check = true;
                } catch (Exception e) {
                    System.out.println("Không được nhập ký tự khác ngoài số! Nhập lại: ");
                    check = false;
                    continue;
                }
                if (subjectNumber <= 0 || subjectNumber > MainRun.subjects.size()) { // <---- dùng subjectList.size()
                    System.out.print("Số môn giảng viên dạy phải lớn hơn 0 và nhỏ hơn tổng số môn! Nhập lại: ");
                    check = false;
                }
            } while (!check);
            List<TeachingTimeSheet> tableList = new ArrayList<>();
            for (int j = 0; j < subjectNumber; j++) {
                System.out.print("Nhập id môn thứ " + (j + 1) + " mà giảng viên " + MainRun.teachers.get(i).getName() + " dạy: ");
                int subjectId;
                int totalClass = 0;
                do {
                    try {
                        subjectId = new Scanner(System.in).nextInt();
                        check = true;
                    } catch (Exception e) {
                        System.out.println("không được có ký tự khác ngoài số! Nhập lại: ");
                        check = false;
                        continue;
                    }
                    Subject subject = searchSubjectId(subjectId);
                    if (subject != null) {
                        System.out.println("Nhập số lớp của môn thứ " + (j + 1) + " mà giảng viên " + MainRun.teachers.get(i).getName() + " dạy:");
                        do {
                            try {
                                totalClass = new Scanner(System.in).nextInt();
                                check = true;
                            } catch (Exception e) {
                                System.out.println("Không được có ký tự khác ngoài số! Nhập lại: ");
                                continue;
                            }
                            if (totalClass <= 0 || totalClass > 3) {
                                System.out.print("Số lớp dạy phải lớn hơn 0 và không lớn hơn 3! Nhập lại: ");
                                check = false;
                            }
                        } while (!check);
                        // viết hàm tính tổng số tiết của giảng viên
                        tableList.add(new TeachingTimeSheet(subject, totalClass));
                        break;
                    } else System.out.print("Không có id môn học vừa nhập! Nhập lại: ");
                }
                while (true);
                System.out.println();
            }
            Teaching teaching = new Teaching(MainRun.teachers.get(i), tableList);
            MainRun.teachings.add(teaching);
        }
        // TODO - write to DB
    }

    public Subject searchSubjectId(int id) {
        List<Subject> resultList = MainRun.subjects.stream().filter(subject -> subject.getId() == id).collect(Collectors.toList());
        return !resultList.isEmpty() ? resultList.get(0) : null;
    }

}
