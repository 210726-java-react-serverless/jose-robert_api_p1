package com.Revature.StudentManagementApp.web.util;

import com.Revature.StudentManagementApp.dataSource.repos.StudentRepo;
import com.Revature.StudentManagementApp.services.StudentService;
import com.Revature.StudentManagementApp.util.MongoConnection;
import com.Revature.StudentManagementApp.web.servlets.HealthCheckServlet;
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

        HealthCheckServlet healthCheckServlet = new HealthCheckServlet();

        ServletContext context = sce.getServletContext();
        context.addServlet("HealthCheckServlet", healthCheckServlet).addMapping("/test");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
        MongoConnection.getInstance().closeIt();
    }
}
