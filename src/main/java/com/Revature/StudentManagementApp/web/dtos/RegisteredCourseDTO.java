package com.Revature.StudentManagementApp.web.dtos;

import java.util.Objects;

public class RegisteredCourseDTO {

    String user_name;
    String course_code;

    public RegisteredCourseDTO(String user_name, String course_code) {
        this.user_name = user_name;
        this.course_code = course_code;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegisteredCourseDTO)) return false;
        RegisteredCourseDTO that = (RegisteredCourseDTO) o;
        return getUser_name().equals(that.getUser_name()) && getCourse_code().equals(that.getCourse_code());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser_name(), getCourse_code());
    }


    @Override
    public String toString() {
        return "RegisteredCourseDTO{" +
                "user_name='" + user_name + '\'' +
                ", course_code='" + course_code + '\'' +
                '}';
    }
}
