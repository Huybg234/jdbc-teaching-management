package main;

import entity.Subject;
import entity.Teacher;
import teachingtimesheet.Teaching;
import teachingtimesheet.TeachingTimeSheet;
import util.CollectionUtil;
import util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TeachingTimeSheetCreator {
    private int teachingSubjectNumber;

    public boolean isValidSubjectAndTeacher() {
        return !CollectionUtil.isEmpty(MainRun.subjects) && !CollectionUtil.isEmpty(MainRun.teachers);
    }

    public void createTeachingTable() {
        if (!isValidSubjectAndTeacher()) {
            System.out.println("Bạn cần nhập giảng viên và môn học trước khi thống kê! ");
            return;
        }
        List<Teaching> tempTeachings = new ArrayList<>();
        for (int i = 0; i < MainRun.teachers.size(); i++) {
            String teacherName = MainRun.teachers.get(i).getName();
            System.out.println("------Kê khai cho giảng viên " + teacherName + "----------");
            System.out.print("Nhập số môn học mà giảng viên " + teacherName + " dạy :");
            int teachingSubjectNumber = inputTeachingSubjectNumber();
            if (teachingSubjectNumber == 0){
                continue;
            }
            List<TeachingTimeSheet> teachingTimeSheets = new ArrayList<>();
            for (int j = 0; j < teachingSubjectNumber; j++) {
                System.out.print("Nhập id môn thứ " + (j + 1) + " mà giảng viên " + teacherName + " dạy: ");
                Subject subject = inputSubjectId();

                System.out.println("Nhập số lớp của môn thứ " + (j + 1) + " mà giảng viên " + teacherName + " dạy:");
                int teachingClass = inputTeachingClass(teachingTimeSheets, subject);
                teachingTimeSheets.add(new TeachingTimeSheet(subject, teachingClass));
            }
            Teaching teaching = new Teaching(MainRun.teachers.get(i), teachingTimeSheets);
            teaching.setTotalLesson(calculateTotalLesson(teachingTimeSheets));
            tempTeachings.add(teaching);
            MainRun.teachings.add(teaching);
        }
        MainRun.teachingDao.insertNewTeachingTimeSheet(tempTeachings);
    }


    private int inputTeachingSubjectNumber() {
        boolean isValidSubjectNumber = true;
        do {
            try {
                teachingSubjectNumber = new Scanner(System.in).nextInt();
                isValidSubjectNumber = true;
            } catch (Exception e) {
                System.out.println("Không được nhập ký tự khác ngoài số! Nhập lại: ");
                isValidSubjectNumber = false;
                continue;
            }
            if (teachingSubjectNumber < 0 || teachingSubjectNumber > MainRun.subjects.size()) {
                System.out.print("Số môn giảng viên dạy phải lớn hơn 0 và nhỏ hơn tổng số môn! Nhập lại: ");
                isValidSubjectNumber = false;
            }

        } while (!isValidSubjectNumber);
        return teachingSubjectNumber;
    }

    public Subject inputSubjectId() {
        int subjectId = 0;
        boolean isValidSubjectId = true;
        do {
            try {
                subjectId = new Scanner(System.in).nextInt();
                isValidSubjectId = true;
            } catch (Exception e) {
                System.out.println("không được có ký tự khác ngoài số! Nhập lại: ");
                isValidSubjectId = false;
                continue;
            }
            Subject subject = searchSubjectId(subjectId);
            if (ObjectUtil.isEmpty(subject)) {
                System.out.print("Không có id môn học vừa nhập! Nhập lại: ");
                isValidSubjectId = false;
            }
        } while (!isValidSubjectId);
        return null;
    }

    private int inputTeachingClass(List<TeachingTimeSheet> teachingTimeSheets, Subject subject) {
        int teachingClass = 0;
        boolean isValidTeachingClass = true;
        do {
            try {
                teachingClass = new Scanner(System.in).nextInt();
                isValidTeachingClass = true;
            } catch (Exception e) {
                System.out.println("Không được có ký tự khác ngoài số! Nhập lại: ");
                isValidTeachingClass = false;
                continue;
            }
            if (teachingClass <= 0 || teachingClass > 3) {
                System.out.print("Số lớp dạy phải lớn hơn 0 và không lớn hơn 3! Nhập lại: ");
                isValidTeachingClass = false;
                continue;
            }
            int currentTotalLesson = calculateTotalLesson(teachingTimeSheets);
            if ((currentTotalLesson + teachingClass * subject.getTotalLesson()) > 200) {
                System.out.println("Tổng số tiết giảng dạy của giảng viên đang là " + currentTotalLesson + ", nếu dạy thêm " + teachingClass + " thì sẽ quá 200 tiết, xin mời nhập lại số lớp mà giảng viên sẽ dạy: ");
                isValidTeachingClass = false;
            }
        } while (!isValidTeachingClass);
        return teachingClass;
    }

    private Subject searchSubjectId(int id) {
        Optional<Subject> subjectOptional = MainRun.subjects.stream().filter(subject -> subject.getId() == id).findFirst();
        return subjectOptional.orElse(null);
    }

    private Teacher searchTeacherId(int id) {
        List<Teacher> collect = MainRun.teachers.stream().filter(teacher -> teacher.getId() == id).collect(Collectors.toList());
        if (!CollectionUtil.isEmpty(collect)) {
            collect.get(0);
        }
        return null;
    }

    private int calculateTotalLesson(List<TeachingTimeSheet> teachingTimeSheets) {
        return CollectionUtil.isEmpty(teachingTimeSheets) ? 0 : teachingTimeSheets.stream().mapToInt(timeSheet -> timeSheet.getSubject().getTotalLesson() * timeSheet.getTotalClass()).sum();
    }
}
