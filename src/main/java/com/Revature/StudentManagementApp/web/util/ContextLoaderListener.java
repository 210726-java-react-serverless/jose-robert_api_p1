package com.Revature.StudentManagementApp.web.util;

import com.Revature.StudentManagementApp.web.servlets.*;
import com.Revature.StudentManagementApp.dataSource.repos.CourseRepo;
import com.Revature.StudentManagementApp.dataSource.repos.FacultyRepo;
import com.Revature.StudentManagementApp.dataSource.repos.RegistrationRepo;
import com.Revature.StudentManagementApp.dataSource.repos.StudentRepo;
import com.Revature.StudentManagementApp.services.CourseService;
import com.Revature.StudentManagementApp.services.FacultyService;
import com.Revature.StudentManagementApp.services.RegistrationService;
import com.Revature.StudentManagementApp.services.StudentService;
import com.Revature.StudentManagementApp.util.MongoConnection;
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

        // Student Servlet
        StudentRepo studentRepo = new StudentRepo();
        StudentService studentService = new StudentService(studentRepo);
        StudentServlet studentServlet = new StudentServlet(studentService, mapper);

        // Course Servlet
        CourseRepo courseRepo = new CourseRepo();
        RegistrationRepo registrationRepo = new RegistrationRepo();
        RegistrationService registrationService = new RegistrationService(registrationRepo);
        RegistrationServlet registrationServlet = new RegistrationServlet(registrationService,mapper);

        CourseRepo courseRepo = new CourseRepo();
        CourseService courseService = new CourseService(courseRepo, registrationService);
        CourseServlet courseServlet = new CourseServlet(courseService,registrationService, mapper);

        // Faculty Servlet
        FacultyRepo facultyRepo = new FacultyRepo();
        FacultyService facultyService = new FacultyService(facultyRepo);
        FacultyServlet facultyServlet = new FacultyServlet(facultyService, mapper);

        // Health Check
        HealthCheckServlet healthCheckServlet = new HealthCheckServlet();

        // Auth Servlets
        AuthServlet authServlet = new AuthServlet(studentService, mapper);
        FacultyAuthServlet facultyAuthServlet = new FacultyAuthServlet(facultyService, mapper);


        ServletContext context = sce.getServletContext();
        context.addServlet("HealthCheckServlet", healthCheckServlet).addMapping("/test");
        context.addServlet("AuthServlet", authServlet).addMapping("/auth");
        context.addServlet("FacultyAuthServlet", facultyAuthServlet).addMapping("/faculty/auth");
        context.addServlet("StudentServlet", studentServlet).addMapping("/students");
        context.addServlet("FacultyServlet", facultyServlet).addMapping("/faculty");
        context.addServlet("CourseServlet", courseServlet).addMapping("/course");
        context.addServlet("RegistrationServlet", registrationServlet).addMapping("/register");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("exitting");
        MongoConnection.getInstance().closeIt();
    }
}
