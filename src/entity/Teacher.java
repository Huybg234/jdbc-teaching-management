package entity;

import java.util.Scanner;

public class Teacher extends Person {

    private static int AUTO_ID = 100;

    private static final String PROFESSOR = "Giáo sư - Tiến sĩ";
    private static final String ASSOCIATE_PROFESSOR = "Phó giáo sư - Tiến sĩ";
    private static final String LECTURER = "Giảng viên chính";
    private static final String MASTER = "Thạc sĩ";


    private String level;

    public Teacher() {
    }

    public Teacher(String level) {
        this.level = level;
    }

    public Teacher(String name, String address, String phoneNumber, String level) {
        super(name, address, phoneNumber);
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", level='" + level + '\'' +
                '}';
    }

    @Override
    public void inputInfo() {
        super.inputInfo();

        this.setId(Teacher.AUTO_ID);
        System.out.println("Nhập trình độ giảng viên: ");
        System.out.println("1. Giáo sư - Tiến sĩ");
        System.out.println("2. Phó giáo sư - Tiến sĩ");
        System.out.println("3. Giảng viên chính");
        System.out.println("4. Thạc sĩ");
        System.out.print("Xin hãy lựa chọn: ");
        boolean check;
        do {
            int choice;
            try {
                choice = new Scanner(System.in).nextInt();
                check = true;
            } catch (Exception e) {
                System.out.print("Không được nhập ký tự khác ngoài số! Vui lòng thử lại: ");
                check = false;
                continue;
            }
            if (choice <= 0 || choice > 4) {
                System.out.print("Nhập số trong khoảng từ 1 đến 4! Vui lòng thử lại: ");
                check = false;
                continue;
            }

            switch (choice) {
                case 1:
                    this.setLevel(Teacher.PROFESSOR);
                    break;
                case 2:
                    this.setLevel(Teacher.ASSOCIATE_PROFESSOR);
                    break;
                case 3:
                    this.setLevel(Teacher.LECTURER);
                    break;
                case 4:
                    this.setLevel(Teacher.MASTER);
                    break;
                default:
                    System.out.print("Trình độ vừa chọn không hợp lệ, vui lòng chọn lại: ");
                    check = false;
                    break;
            }
        } while (!check);
        Teacher.AUTO_ID++;
    }

}
