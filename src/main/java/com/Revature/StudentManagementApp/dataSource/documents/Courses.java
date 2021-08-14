package com.Revature.StudentManagementApp.dataSource.documents;

import java.util.Objects;

public class Courses {

    private String course_id;
    private String course_name;
    private String course_code;
    private String start_date;
    private String end_date;
    private boolean isvailable = true;


    @Override
    public String toString() {
        return "Courses{" +
                "course_name='" + course_name + '\'' +
                ", course_code='" + course_code + '\'' +
                ", start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", isvailable=" + isvailable +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Courses)) return false;
        Courses courses = (Courses) o;
        return Objects.equals(getCourse_name(), courses.getCourse_name()) && Objects.equals(getCourse_code(), courses.getCourse_code()) && Objects.equals(getStart_date(), courses.getStart_date()) && Objects.equals(getEnd_date(), courses.getEnd_date());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCourse_name(), getCourse_code(), getStart_date(), getEnd_date());
    }

    public Courses(){};

    public Courses(String course_name, String course_code, String start_date, String end_date) {
        this.course_name = course_name;
        this.course_code = course_code;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public boolean isIsvailable() {
        return isvailable;
    }

    public void setIsvailable(boolean isvailable) {
        this.isvailable = isvailable;
    }




    }
