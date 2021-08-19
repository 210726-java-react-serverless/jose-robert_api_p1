package com.Revature.StudentManagementApp.web.dtos;

import com.Revature.StudentManagementApp.dataSource.documents.Faculty;
import com.Revature.StudentManagementApp.dataSource.documents.Student;

import java.util.Objects;

public class Principal {

    private String id;
    private String username;

    public Principal() {
        super();
    }

    public Principal(Student subject) {
        this.id = subject.getStudent_Id();
        this.username = subject.getUser().getUser_name();
    }

    public Principal(Faculty subject) {
        this.id = subject.getId();
        this.username = subject.getUser().getUser_name();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Principal principal = (Principal) o;
        return Objects.equals(id, principal.id) && Objects.equals(username, principal.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

    @Override
    public String toString() {
        return "Principal{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
