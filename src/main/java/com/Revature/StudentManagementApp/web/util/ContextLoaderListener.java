package com.Revature.StudentManagementApp.web.util;

import com.Revature.StudentManagementApp.dataSource.repos.FacultyRepo;
import com.Revature.StudentManagementApp.dataSource.repos.StudentRepo;
import com.Revature.StudentManagementApp.services.FacultyService;
import com.Revature.StudentManagementApp.services.StudentService;
import com.Revature.StudentManagementApp.util.MongoConnection;
import com.Revature.StudentManagementApp.web.servlets.AuthServlet;
import com.Revature.StudentManagementApp.web.servlets.FacultyServlet;
import com.Revature.StudentManagementApp.web.servlets.HealthCheckServlet;
import com.Revature.StudentManagementApp.web.servlets.StudentServlet;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoaderListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        MongoClient client = MongoConnection.getInstance().getConnection();
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

        StudentRepo studentRepo = new StudentRepo();
        StudentService studentService = new StudentService(studentRepo);

        FacultyRepo facultyRepo = new FacultyRepo();
        FacultyService facultyService = new FacultyService(facultyRepo);

        HealthCheckServlet healthCheckServlet = new HealthCheckServlet();
        StudentServlet studentServlet = new StudentServlet(studentService, mapper);
        FacultyServlet facultyServlet = new FacultyServlet(facultyService, mapper);
        AuthServlet authServlet = new AuthServlet(studentService, mapper);

        ServletContext context = sce.getServletContext();
        context.addServlet("HealthCheckServlet", healthCheckServlet).addMapping("/test");
        context.addServlet("StudentServlet", studentServlet).addMapping("/student");
        context.addServlet("FacultyServlet", facultyServlet).addMapping("/faculty");
        context.addServlet("AuthServlet", authServlet).addMapping("/auth");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("exitting");
        MongoConnection.getInstance().closeIt();
    }
}
