package com.Revature.StudentManagementApp.web.dtos;

import com.Revature.StudentManagementApp.dataSource.documents.Address;
import com.Revature.StudentManagementApp.dataSource.documents.SchoolUser;
import com.Revature.StudentManagementApp.dataSource.documents.Student;

import java.util.Objects;

public class StudentDTO {

    private String student_Id;
    private String Major;
    private SchoolUserDTO user;


    public StudentDTO(Student student){
        this.student_Id = student.getStudent_Id();
        this.Major = student.getMajor();
        this.user = new SchoolUserDTO(student.getUser());
    };


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
        Major = major;
    }

    public SchoolUserDTO getUser() {
        return user;
    }

    public void setUser(SchoolUserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentDTO)) return false;
        StudentDTO that = (StudentDTO) o;
        return getStudent_Id().equals(that.getStudent_Id()) && getMajor().equals(that.getMajor()) && getUser().equals(that.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStudent_Id(), getMajor(), getUser());
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
                "student_Id='" + student_Id + '\'' +
                ", Major='" + Major + '\'' +
                ", user=" + user +
                '}';
    }
}
