package com.Revature.StudentManagementApp.web.servlets;

import com.Revature.StudentManagementApp.dataSource.documents.Courses;
import com.Revature.StudentManagementApp.services.CourseService;
import com.Revature.StudentManagementApp.services.RegistrationService;
import com.Revature.StudentManagementApp.util.exceptions.DataSourceException;
import com.Revature.StudentManagementApp.util.exceptions.InvalidRequestException;
import com.Revature.StudentManagementApp.web.dtos.CourseDTO;
import com.Revature.StudentManagementApp.web.dtos.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CourseServlet extends HttpServlet {

    private final CourseService courseService;
    private final RegistrationService registrationService;
    private final ObjectMapper mapper;

    public CourseServlet(CourseService courseService, RegistrationService registrationService, ObjectMapper mapper) {
        this.courseService = courseService;
        this.registrationService = registrationService;
        this.mapper = mapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println(req.getAttribute("filtered"));
        PrintWriter printWriter = resp.getWriter();
        resp.setContentType("applicatioin/json");


        String course_code = req.getParameter("course_code");


        try{
            if(course_code == null){
                List<Courses> Allcourse =  registrationService.listCoursesOffered();
                printWriter.write(mapper.writeValueAsString(Allcourse));
            }else{
                CourseDTO course = new CourseDTO(registrationService.findByCourseCode(course_code));
                System.out.println(course);
                printWriter.write(mapper.writeValueAsString(course));
            }
        }catch (DataSourceException rnfe) {
            resp.setStatus(404);
            ErrorResponse errResp = new ErrorResponse(404, rnfe.getMessage());
            printWriter.write(mapper.writeValueAsString(errResp));
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500); // server's fault
            ErrorResponse errResp = new ErrorResponse(500, "The server experienced an issue, please try again later.");
            printWriter.write(mapper.writeValueAsString(errResp));
        }
    }





    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getAttribute("filtered"));
        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("application/json");



        try {
            Courses newCourse = mapper.readValue(req.getInputStream(), Courses.class);
            CourseDTO principal = new CourseDTO(courseService.addCourseToCatalog(newCourse));
            String payload = mapper.writeValueAsString(principal);
            respWriter.write(payload);
            resp.setStatus(201); // 201 - Created
        } catch (InvalidRequestException | MismatchedInputException e) {
            e.printStackTrace();
            resp.setStatus(400); // 400 - Bad Request
            ErrorResponse errResp = new ErrorResponse(400, e.getMessage());
            respWriter.write(mapper.writeValueAsString(errResp));
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
        }
    }
}
