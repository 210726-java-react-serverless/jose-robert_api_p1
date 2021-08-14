package com.Revature.StudentManagementApp.services;

import com.Revature.StudentManagementApp.dataSource.documents.Student;
import com.Revature.StudentManagementApp.dataSource.repos.StudentRepo;
import com.Revature.StudentManagementApp.util.exceptions.InvalidRequestException;

import javax.naming.AuthenticationException;

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



    public Student login(String username, String password)  {


        Student student = stu_repo.findUserByCredentials(username, password);

        if (student == null) {
            try {
                throw new AuthenticationException("Invalid user");
            } catch (AuthenticationException e) {
                e.printStackTrace();
            }

        }



        return student;
    }

}
