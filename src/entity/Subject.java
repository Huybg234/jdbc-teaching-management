package entity;

import java.util.Scanner;

public class Subject {

    private int id;
    private String name;
    private int totalLesson;
    private int theoryLesson;
    private float unitTheoryCost;

    private static int AUTO_ID = 100;

    public Subject() {
    }

    public Subject(int id, String name, int totalLesson, int theoryLesson, float expense) {
        this.id = id;
        this.name = name;
        this.totalLesson = totalLesson;
        this.theoryLesson = theoryLesson;
        this.unitTheoryCost = expense;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalLesson() {
        return totalLesson;
    }

    public void setTotalLesson(int totalLesson) {
        this.totalLesson = totalLesson;
    }

    public int getTheoryLesson() {
        return theoryLesson;
    }

    public void setTheoryLesson(int theoryLesson) {
        this.theoryLesson = theoryLesson;
    }

    public float getUnitTheoryCost() {
        return unitTheoryCost;
    }

    public void setUnitTheoryCost(float unitTheoryCost) {
        this.unitTheoryCost = unitTheoryCost;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", totalLesson=" + totalLesson +
                ", theoryLesson=" + theoryLesson +
                ", expense=" + unitTheoryCost +
                '}';
    }

    public void inputInfo() {
        this.setId(Subject.AUTO_ID);

        System.out.print("Nhập tên môn học: ");
        this.setName(new Scanner(System.in).nextLine());

        boolean lessonTotalCheck;
        System.out.print("Nhập tổng số tiết học: ");
        do {
            try {
                this.setTotalLesson(new Scanner(System.in).nextInt());
                lessonTotalCheck = true;
            } catch (Exception e) {
                System.out.print("Không được nhập ký tự khác ngoài số! Nhập lại: ");
                lessonTotalCheck = false;
                continue;
            }
            if (this.totalLesson <= 0) {
                System.out.print("Tổng số tiết không được âm! Nhập lại: ");
                lessonTotalCheck = false;
            }
        } while (!lessonTotalCheck);


        System.out.print("Nhập số tiết lý thuyết: ");
        boolean theoryLessonCheck;
        do {
            try {
                this.setTheoryLesson(new Scanner(System.in).nextInt());
                theoryLessonCheck = true;
            } catch (Exception e) {
                System.out.print("Không được nhập ký tự khác ngoài số! Nhập lại: ");
                theoryLessonCheck = false;
                continue;
            }
            if (this.theoryLesson <= 0) {
                System.out.print("Số tiết lý thuyết không được âm! Nhập lại: ");
                theoryLessonCheck = false;
            }
        } while (!theoryLessonCheck);

        System.out.println("Nhập mức kinh phí cho một tiết học lý thuyết: ");
        boolean expenseCheck;
        do {
            try {
                this.setUnitTheoryCost(new Scanner(System.in).nextFloat());
                expenseCheck = true;
            } catch (Exception e) {
                System.out.print("Không được nhập ký tự khác ngoài số! Nhập lại: ");
                expenseCheck = false;
                continue;
            }
            if (this.unitTheoryCost <= 0) {
                System.out.print("Mức kinh phí không được âm! Nhập lại: ");
                expenseCheck = false;
            }
        } while (!expenseCheck);

        Subject.AUTO_ID++;
    }

}
