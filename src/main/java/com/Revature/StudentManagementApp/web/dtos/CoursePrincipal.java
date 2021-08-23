package com.Revature.StudentManagementApp.web.dtos;

import com.Revature.StudentManagementApp.dataSource.documents.Courses;

import java.util.Objects;

public class CoursePrincipal {

    private String course_code;
    private String field;
    private String updateTo;

    public CoursePrincipal() {
        super();
    }

    public CoursePrincipal(Courses subject) {
        this.course_code = subject.getCourse_code();
    }

    public CoursePrincipal(String course_code, String field, String updateTo) {
        this.course_code = course_code;
        this.field = field;
        this.updateTo = updateTo;
    }


    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getUpdateTo() {
        return updateTo;
    }

    public void setUpdateTo(String updateTo) {
        this.updateTo = updateTo;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CoursePrincipal)) return false;
        CoursePrincipal principal = (CoursePrincipal) o;
        return getCourse_code().equals(principal.getCourse_code()) && getField().equals(principal.getField()) && getUpdateTo().equals(principal.getUpdateTo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCourse_code(), getField(), getUpdateTo());
    }


    @Override
    public String toString() {
        return "CoursePrincipal{" +
                "course_code='" + course_code + '\'' +
                ", field='" + field + '\'' +
                ", updateTo='" + updateTo + '\'' +
                '}';
    }


}
