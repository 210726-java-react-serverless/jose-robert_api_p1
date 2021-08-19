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

        ///Studeent Servlet Set up
        StudentRepo studentRepo = new StudentRepo();
        StudentService studentService = new StudentService(studentRepo);
        StudentServlet studentServlet = new StudentServlet(studentService, mapper);


        //Faculty Servlet set up
        FacultyRepo facultyRepo = new FacultyRepo();
        FacultyService facultyService = new FacultyService(facultyRepo);
        FacultyServlet facultyServlet = new FacultyServlet(facultyService, mapper);

        //Course Servlet set up
        RegistrationRepo registrationRepo = new RegistrationRepo();
        CourseRepo courseRepo = new CourseRepo();
        RegistrationService registrationService = new RegistrationService(registrationRepo);
        CourseService courseService = new CourseService(courseRepo, registrationService);
        CourseServlet courseServlet = new CourseServlet(courseService, registrationService, mapper);


        HealthCheckServlet healthCheckServlet = new HealthCheckServlet();
        AuthServlet authServlet = new AuthServlet(studentService, mapper);

        ServletContext context = sce.getServletContext();
        context.addServlet("HealthCheckServlet", healthCheckServlet).addMapping("/test");
        context.addServlet("AuthServlet", authServlet).addMapping("/auth");
        context.addServlet("Studentservlet", studentServlet).addMapping("/students");
        context.addServlet("Facultyservlet", facultyServlet).addMapping("/faculty");
        context.addServlet("Courseservlet", courseServlet).addMapping("/course");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("exitting");
        MongoConnection.getInstance().closeIt();
    }
}
