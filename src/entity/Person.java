package entity;

import java.util.Scanner;

public class Person {
    protected int id;
    protected String name;
    protected String address;
    protected String phoneNumber;

    public Person() {
    }

    public Person(int id, String name, String address, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void inputInfo() {
        System.out.print("Nhập tên: ");
        this.setName(new Scanner(System.in).nextLine());
        System.out.print("Nhập địa chỉ: ");
        this.setAddress(new Scanner(System.in).nextLine());
        System.out.print("Nhập số điện thoại: ");
        this.setPhoneNumber(new Scanner(System.in).nextLine());
    }

}
