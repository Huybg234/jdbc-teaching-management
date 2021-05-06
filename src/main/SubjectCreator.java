package main;

import entity.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SubjectCreator {

    public void createNewSubject() {
        System.out.println("Nhập số lượng môn học muốn thêm mới: ");
        int subjectQuantity = 0;
        boolean isValidSubjectQuantity = true;
        do {
            try {
                subjectQuantity = new Scanner(System.in).nextInt();
                isValidSubjectQuantity = true;
            } catch (Exception e) {
                System.out.println("Không được nhập ký tự khác ngoài số! Nhập lại: ");
                isValidSubjectQuantity = false;
                continue;
            }
            if (subjectQuantity <= 0) {
                System.out.print("Số lượng môn học không được nhỏ hơn 0! Nhập lại: ");
                isValidSubjectQuantity = false;
            }
        } while (!isValidSubjectQuantity);

        List<Subject> tempSubjects = new ArrayList<>();
        for (int i = 0; i < subjectQuantity; i++) {
            Subject subject = new Subject();
            subject.inputInfo();
            tempSubjects.add(subject);
        }
        MainRun.subjects.addAll(tempSubjects);
        MainRun.subjectDao.insertNewSubject(tempSubjects);
    }
}
