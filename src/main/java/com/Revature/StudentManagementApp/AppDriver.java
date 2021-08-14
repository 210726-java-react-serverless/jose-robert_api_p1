package com.Revature.StudentManagementApp;

import com.Revature.StudentManagementApp.util.AppServer;

public class AppDriver {

    public static void main(String[] args) {
        AppServer app = AppServer.getInstance();
        app.startup();
    }

}