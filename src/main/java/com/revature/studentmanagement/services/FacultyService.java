package com.revature.studentmanagement.services;

import com.revature.studentmanagement.datasource.documents.Faculty;
import com.revature.studentmanagement.datasource.repos.FacultyRepo;
import com.revature.studentmanagement.util.exceptions.AuthenticationException;
import com.revature.studentmanagement.util.exceptions.DataSourceException;
import com.revature.studentmanagement.util.exceptions.InvalidRequestException;

public class FacultyService {


    private final FacultyRepo faculty_repo;



    public FacultyService(FacultyRepo faculty_repo){

        this.faculty_repo = faculty_repo;
    }



    //TODO student register method AND FACULTY
    public Faculty register(Faculty new_user){

        if (!isUserValid(new_user)) {
            //TODO logging goes here.
            throw new InvalidRequestException("Invalid user data provided!");

        }
        return faculty_repo.save(new_user);
    }


    public boolean isUserValid(Faculty user) {
        if (user == null) return false;
        if (user.getUser().getLast_name() == null || user.getUser().getLast_name().trim().equals("")) return false;
        if (user.getUser().getEmail() == null || user.getUser().getEmail().trim().equals("")) return false;
        if (user.getUser().getUser_name() == null || user.getUser().getUser_name().trim().equals("")) return false;
        return user.getUser().getPassword() != null && !user.getUser().getPassword().trim().equals("");
    }


    public Faculty login(String username, String password) {
        if (username == null || username.trim().equals("") || password == null || password.trim().equals("")) {
            throw new AuthenticationException("Did not provide username or password");
        }

        try {
            return faculty_repo.findUserByCredentials(username, password);
        } catch (Exception e) {
            throw new DataSourceException("An unexpected error occurred", e);
        }
    }

}
