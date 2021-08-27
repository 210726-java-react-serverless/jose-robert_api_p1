package com.revature.studentmanagement.datasource.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Student {

    private String student_Id;
    private String Major;
    private SchoolUser user;

    public Student() {
        super();
    }

    public Student(String major, String fn, String ln, String dob, String phone, String username, String password, String email, Address address) {
        this.Major = major;
        this.user = new SchoolUser(fn, ln, dob, phone, username, password, email, address);
    }

    public Student(String major, String fn, String ln, String dob, String phone, String username, String password, String email) {
        this.Major = major;
        this.user = new SchoolUser(fn, ln, dob, phone, username, password, email);
    }

    public String toFile() {

        StringBuilder builder = new StringBuilder();
        builder.append(student_Id).append(":")
                .append(user.getFirst_name()).append(" ").append(user.getLast_name()).append(":")
                .append(user.getEmail()).append(":")
                .append(user.getUser_name()).append(":")
                .append(user.getPassword()).append("\n");

        return builder.toString();
    }

    @Override
    public String toString() {
        return "Student{" +
                "student_Id=" + student_Id +
                ", Major='" + Major + '\'' +
                ", user=" + user +
                '}';
    }
    public String getStudent_Id() {
        return student_Id;
    }

    public void setStudent_Id(String student_Id) {
        this.student_Id = student_Id;
    }

    public String getMajor() {
        return Major;
    }

    public void setMajor(String major) {
        this.Major = major;
    }

    public SchoolUser getUser() {
        return user;
    }

    public void setUser(SchoolUser user) {
        this.user = user;
    }
}
