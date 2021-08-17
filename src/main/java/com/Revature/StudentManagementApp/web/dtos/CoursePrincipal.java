package com.Revature.StudentManagementApp.web.dtos;

import com.Revature.StudentManagementApp.dataSource.documents.Courses;

import java.util.Objects;

public class CoursePrincipal {

    private String course_code;

    public CoursePrincipal() {
        super();
    }

    public CoursePrincipal(Courses subject) {
        this.course_code = subject.getCourse_code();
    }

    public String getCourse_course() {
        return course_code;
    }

    public void setCourse_course(String course_course) {
        this.course_code = course_course;
    }

    @Override
    public String toString() {
        return "CoursePrincipal{" +
                "course_course='" + course_code + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoursePrincipal that = (CoursePrincipal) o;
        return course_code.equals(that.course_code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course_code);
    }
}
