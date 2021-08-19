package com.Revature.StudentManagementApp.web.dtos;

import com.Revature.StudentManagementApp.dataSource.documents.Courses;

import java.util.Objects;

public class CourseDTO {

    private String course_name;
    private String course_code;
    private String start_date;
    private String end_date;


    CourseDTO(Courses course){
        this.course_name = course.getCourse_name();
        this.course_code = course.getCourse_code();
        this.start_date = course.getStart_date();
        this.end_date = course.getEnd_date();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseDTO)) return false;
        CourseDTO courseDTO = (CourseDTO) o;
        return getCourse_name().equals(courseDTO.getCourse_name()) && getCourse_code().equals(courseDTO.getCourse_code()) && getStart_date().equals(courseDTO.getStart_date()) && getEnd_date().equals(courseDTO.getEnd_date());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCourse_name(), getCourse_code(), getStart_date(), getEnd_date());
    }


    @Override
    public String toString() {
        return "CourseDTO{" +
                "course_name='" + course_name + '\'' +
                ", course_code='" + course_code + '\'' +
                ", start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                '}';
    }
}
