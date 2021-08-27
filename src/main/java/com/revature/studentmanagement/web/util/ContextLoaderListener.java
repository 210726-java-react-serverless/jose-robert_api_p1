package com.revature.studentmanagement.web.util;

import com.revature.studentmanagement.web.filters.AuthFilter;
import com.revature.studentmanagement.web.servlets.*;
import com.revature.studentmanagement.datasource.repos.CourseRepo;
import com.revature.studentmanagement.datasource.repos.FacultyRepo;
import com.revature.studentmanagement.datasource.repos.RegistrationRepo;
import com.revature.studentmanagement.datasource.repos.StudentRepo;
import com.revature.studentmanagement.services.CourseService;
import com.revature.studentmanagement.services.FacultyService;
import com.revature.studentmanagement.services.RegistrationService;
import com.revature.studentmanagement.services.StudentService;
import com.revature.studentmanagement.util.MongoConnection;
import com.revature.studentmanagement.web.util.security.JwtConfig;
import com.revature.studentmanagement.web.util.security.TokenGenerator;
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

        // Student Servlet
        StudentRepo studentRepo = new StudentRepo();
        StudentService studentService = new StudentService(studentRepo);
        StudentServlet studentServlet = new StudentServlet(studentService, mapper);

        // Course Servlet
        CourseRepo courseRepo = new CourseRepo();
        RegistrationRepo registrationRepo = new RegistrationRepo();
        RegistrationService registrationService = new RegistrationService(registrationRepo);
        RegistrationServlet registrationServlet = new RegistrationServlet(registrationService,mapper);

        CourseService courseService = new CourseService(courseRepo, registrationService);
        CourseServlet courseServlet = new CourseServlet(courseService,registrationService, mapper);

        // Faculty Servlet
        FacultyRepo facultyRepo = new FacultyRepo();
        FacultyService facultyService = new FacultyService(facultyRepo);
        FacultyServlet facultyServlet = new FacultyServlet(facultyService, mapper);

        // Health Check
        HealthCheckServlet healthCheckServlet = new HealthCheckServlet();
        JwtConfig jwt = new JwtConfig();
        TokenGenerator tokenGenerator = new TokenGenerator(jwt);
        AuthServlet authServlet = new AuthServlet(studentService, mapper, tokenGenerator);


        FacultyAuthServlet facultyAuthServlet = new FacultyAuthServlet(facultyService, mapper);



        AuthFilter authFilter = new AuthFilter(jwt);
        ServletContext context = sce.getServletContext();
        context.addFilter("AuthFilter", authFilter).addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
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
