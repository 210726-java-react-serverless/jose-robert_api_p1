package com.Revature.StudentManagementApp.web.util;

import com.Revature.StudentManagementApp.web.filters.AuthFilter;
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
import com.Revature.StudentManagementApp.web.util.security.JwtConfig;
import com.Revature.StudentManagementApp.web.util.security.TokenGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.EnumSet;

public class ContextLoaderListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        MongoClient client = MongoConnection.getInstance().getConnection();
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

        StudentRepo studentRepo = new StudentRepo();
        StudentService studentService = new StudentService(studentRepo);
        StudentServlet studentServlet = new StudentServlet(studentService, mapper);

        RegistrationRepo registrationRepo = new RegistrationRepo();
        RegistrationService registrationService = new RegistrationService(registrationRepo);
        RegistrationServlet registrationServlet = new RegistrationServlet(registrationService,mapper);

        CourseRepo courseRepo = new CourseRepo();
        CourseService courseService = new CourseService(courseRepo, registrationService);
        CourseServlet courseServlet = new CourseServlet(courseService,registrationService, mapper);

        FacultyRepo facultyRepo = new FacultyRepo();
        FacultyService facultyService = new FacultyService(facultyRepo);

        HealthCheckServlet healthCheckServlet = new HealthCheckServlet();
        FacultyServlet facultyServlet = new FacultyServlet(facultyService, mapper);

        JwtConfig jwt = new JwtConfig();
        TokenGenerator tokenGenerator = new TokenGenerator(jwt);
        AuthServlet authServlet = new AuthServlet(studentService, mapper, tokenGenerator);


        AuthFilter authFilter = new AuthFilter(jwt);
        ServletContext context = sce.getServletContext();
        context.addFilter("AuthFilter", authFilter).addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
        context.addServlet("HealthCheckServlet", healthCheckServlet).addMapping("/test");
        context.addServlet("AuthServlet", authServlet).addMapping("/auth");
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
