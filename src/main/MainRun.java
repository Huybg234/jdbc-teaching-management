package main;

import entity.Subject;
import entity.Teacher;
import repository.SubjectDao;
import repository.TeacherDao;
import repository.TeachingDao;
import teachingtimesheet.Teaching;
import util.CollectionUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class MainRun {
    public static List<Subject> subjects;
    public static List<Teacher> teachers;
    public static List<Teaching> teachings;

    public static final TeacherDao teacherDao = new TeacherDao();
    public static final SubjectDao subjectDao = new SubjectDao();
    public static final TeachingDao teachingDao = new TeachingDao();

    private static final SubjectCreator subjectCreator = new SubjectCreator();
    private static final TeacherCreator teacherCreator = new TeacherCreator();
    private static final TeachingTimeSheetCreator teachingTimeSheetCreator = new TeachingTimeSheetCreator();
    private static final TeachingTimeSheetSorterAndCalculator sortTeachingTable = new TeachingTimeSheetSorterAndCalculator();

    public static void main(String[] args) {
        System.out.println("Program is initializing ....");
        init();
        System.out.println("Program is ready!");
        menu();
    }

    private static void init() {
        teachers = !CollectionUtil.isEmpty(teacherDao.getTeachers()) ? teacherDao.getTeachers() : new ArrayList<>();
        Teacher.AUTO_ID = CollectionUtil.isEmpty(teachers) ? 100 : teachers.get(teachers.size() - 1).getId() + 1;
        subjects = !CollectionUtil.isEmpty(subjectDao.getSubjects()) ? subjectDao.getSubjects() : new ArrayList<>();
        if (CollectionUtil.isEmpty(subjects)) {
            Subject.AUTO_ID = 100;
        } else {
            subjects.sort(Comparator.comparing(Subject::getId));
            Subject.AUTO_ID = subjects.get(subjects.size() - 1).getId() + 1;
        }
        teachings = !CollectionUtil.isEmpty(teachingDao.getTeachingTimeSheet()) ? teachingDao.getTeachingTimeSheet() : new ArrayList<>();
    }

    public static void menu() {
        do {
            int functionChoice = functionChoice();
            switch (functionChoice) {
                case 1:
                    createNewSubject();
                    printSubject();
                    break;
                case 2:
                    createNewTeacher();
                    printTeacher();
                    break;
                case 3:
                    createTeachingTable();
                    printTeaching();
                    break;
                case 4:
                    sortTeachingTable.sortTeachingTable();
                    break;
                case 5:
                    sortTeachingTable.teacherIncome();
                    break;
                case 6:
                    System.exit(0);
            }
        } while (true);
    }

    public static int functionChoice() {
        System.out.println("-----QU???N L?? TR??? L????NG CHO GI???NG VI??N-------");
        System.out.println("1. Nh???p danh s??ch m??n h???c");
        System.out.println("2. Nh???p danh s??ch gi???ng vi??n");
        System.out.println("3. L???p b???ng k?? khai gi???ng d???y cho m???i gi???ng vi??n");
        System.out.println("4. S???p x???p danh s??ch k?? khai gi???ng d???y");
        System.out.println("5. L???p b???ng t??nh ti???n c??ng cho m???i gi???ng vi??n");
        System.out.println("6. Tho??t");
        System.out.print("Nh???p s??? l???a ch???n c???a b???n: ");
        int functionChoice = 0;
        boolean checkFunction = true;
        do {
            try {
                functionChoice = new Scanner(System.in).nextInt();
                checkFunction = true;
            } catch (Exception e) {
                System.out.println("Kh??ng ???????c nh???p k?? t??? kh??c ngo??i s???! Nh???p l???i: ");
                continue;
            }
            if (functionChoice <= 0 || functionChoice > 6) {
                System.out.print("Nh???p s??? trong kho???ng t??? 1 ?????n 6! Nh???p l???i: ");
                checkFunction = false;
            } else break;
        } while (!checkFunction);
        return functionChoice;
    }

    public static void createNewSubject() {
        subjectCreator.createNewSubject();
    }

    public static void printSubject() {
        subjects.forEach(System.out::println);
    }

    public static void createNewTeacher() {
        teacherCreator.createNewTeacher();
    }

    public static void printTeacher() {
        teachers.forEach(System.out::println);
    }

    public static void createTeachingTable() {
        teachingTimeSheetCreator.createTeachingTable();
    }

    public static void printTeaching() {
        teachings.forEach(System.out::println);
    }

}
