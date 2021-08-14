package com.Revature.StudentManagementApp.dataSource.documents;

import java.util.Objects;

public class SchoolUser {
    private String first_name;
    private String last_name;
    private String DOB;
    private String phone_num;
    private String user_name;
    private String password;
    private String email;
    private Address address;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public SchoolUser(){};

    public SchoolUser(String first_name, String last_name, String DOB, String phone_num, String user_name, String password, String email, Address address) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.DOB = DOB;
        this.phone_num = phone_num;
        this.user_name = user_name;
        this.password = password;
        this.email = email;
        this.address = address;
    }






    @Override
    public String toString() {
        return "SchoolUser{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", DOB='" + DOB + '\'' +
                ", phone_num='" + phone_num + '\'' +
                ", user_name='" + user_name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SchoolUser)) return false;
        SchoolUser that = (SchoolUser) o;
        return Objects.equals(getFirst_name(), that.getFirst_name())
                && Objects.equals(getLast_name(), that.getLast_name())
                && Objects.equals(getDOB(), that.getDOB())
                && Objects.equals(getAddress(), that.getAddress())
                && Objects.equals(getPhone_num(), that.getPhone_num())
                && Objects.equals(getUser_name(), that.getUser_name())
                && Objects.equals(getPassword(), that.getPassword())
                && Objects.equals(getEmail(), that.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirst_name(), getLast_name(), getDOB(), getAddress(), getPhone_num(), getUser_name(), getPassword(), getEmail());
    }

}
