package com.Revature.StudentManagementApp.util;

import com.Revature.StudentManagementApp.dataSource.repos.StudentRepo;
import com.Revature.StudentManagementApp.services.StudentService;

public class AppServer {

    private static final AppServer APP_SERVER = new AppServer();


    public AppServer(){


        try {
            StudentRepo studentRepo = new StudentRepo();
            StudentService studentService = new StudentService(studentRepo);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void startup(){}

    public static void shutdown(){

    }

    public static AppServer getInstance() {
        return APP_SERVER;
    }
}
