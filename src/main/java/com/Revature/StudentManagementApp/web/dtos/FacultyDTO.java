package com.Revature.StudentManagementApp.web.dtos;

import com.Revature.StudentManagementApp.dataSource.documents.Faculty;
import com.Revature.StudentManagementApp.dataSource.documents.SchoolUser;

import java.util.Objects;

public class FacultyDTO {

    private String Id;
    private Float Salary;
    private String department;
    private SchoolUserDTO user;


    FacultyDTO(Faculty faculty){
        this.Id = faculty.getId();
        this.Salary = faculty.getSalary();
        this.department = faculty.getDepartment();
        this.user = new SchoolUserDTO(faculty.getUser());
    }


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Float getSalary() {
        return Salary;
    }

    public void setSalary(Float salary) {
        Salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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
        if (!(o instanceof FacultyDTO)) return false;
        FacultyDTO that = (FacultyDTO) o;
        return getId().equals(that.getId()) && getSalary().equals(that.getSalary()) && getDepartment().equals(that.getDepartment()) && getUser().equals(that.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSalary(), getDepartment(), getUser());
    }

    @Override
    public String toString() {
        return "FacultyDTO{" +
                "Id='" + Id + '\'' +
                ", Salary=" + Salary +
                ", department='" + department + '\'' +
                ", user=" + user +
                '}';
    }
}
