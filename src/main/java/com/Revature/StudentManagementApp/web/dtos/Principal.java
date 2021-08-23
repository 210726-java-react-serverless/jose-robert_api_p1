package com.Revature.StudentManagementApp.web.dtos;

import com.Revature.StudentManagementApp.dataSource.documents.Faculty;
import com.Revature.StudentManagementApp.dataSource.documents.Student;

import java.util.Objects;

public class Principal {

    private String id;
    private String username;
    private boolean isFaculty;

    public Principal() {
        super();
    }

    public Principal(Student subject) {
        this.id = subject.getStudent_Id();
        this.username = subject.getUser().getUser_name();
        this.isFaculty = false;
    }

    public Principal(Faculty subject) {
        this.id = subject.getId();
        this.username = subject.getUser().getUser_name();
        this.isFaculty = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isFaculty() {
        return isFaculty;
    }

    public void setFaculty(boolean faculty) {
        isFaculty = faculty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Principal principal = (Principal) o;
        return isFaculty == principal.isFaculty && Objects.equals(id, principal.id) && Objects.equals(username, principal.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, isFaculty);
    }

    @Override
    public String toString() {
        return "Principal{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", isFaculty=" + isFaculty +
                '}';
    }
}