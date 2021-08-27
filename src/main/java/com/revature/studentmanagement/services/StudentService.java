package com.revature.studentmanagement.services;

import com.revature.studentmanagement.datasource.documents.Student;
import com.revature.studentmanagement.datasource.repos.StudentRepo;
import com.revature.studentmanagement.util.exceptions.AuthenticationException;
import com.revature.studentmanagement.util.exceptions.InvalidRequestException;

public class StudentService {
    private final StudentRepo stu_repo;



    public StudentService(StudentRepo stu_repo){

        this.stu_repo = stu_repo;

    }




    //TODO student register method AND FACULTY
    public Student register(Student new_user){

        if (!isUserValid(new_user)) {
            throw new InvalidRequestException("Invalid user data provided!");
        }else
            return stu_repo.save(new_user);
    }

    public boolean isUserValid(Student user) {
        if (user == null)return false;
        if (user.getUser().getLast_name() == null || user.getUser().getLast_name().trim().equals("")) return false;
        if (user.getUser().getEmail() == null || user.getUser().getEmail().trim().equals("")) return false;
        if (user.getUser().getUser_name() == null || user.getUser().getUser_name().trim().equals("")) return false;
        return user.getUser().getPassword() != null && !user.getUser().getPassword().trim().equals("");
    }



    public Student login(String username, String password) {
        if (username == null || username.trim().equals("") || password == null || password.trim().equals("")) {
            throw new InvalidRequestException("Invalid user credentials provided");
        }

        Student student = stu_repo.findUserByCredentials(username, password);

        if (student == null) {
            throw new AuthenticationException("Invalid credentials provided");
        }

        return student;
    }
}
