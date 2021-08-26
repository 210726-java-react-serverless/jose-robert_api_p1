package com.Revature.StudentManagementApp.web.servlets;

import com.Revature.StudentManagementApp.dataSource.documents.Courses;
import com.Revature.StudentManagementApp.services.RegistrationService;
import com.Revature.StudentManagementApp.util.exceptions.DataSourceException;
import com.Revature.StudentManagementApp.util.exceptions.InvalidRequestException;
import com.Revature.StudentManagementApp.web.dtos.CourseDTO;
import com.Revature.StudentManagementApp.web.dtos.ErrorResponse;
import com.Revature.StudentManagementApp.web.dtos.RegisteredCourseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class RegistrationServlet  extends HttpServlet {

    private final RegistrationService registrationService;
    private final ObjectMapper mapper;


    public RegistrationServlet(RegistrationService registrationService, ObjectMapper objectMapper) {
        this.registrationService = registrationService;
        this.mapper = objectMapper;
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getAttribute("filtered"));
        PrintWriter printWriter = resp.getWriter();
        resp.setContentType("application/json");

        String user_name = req.getParameter("user_name");

        
        try{
            List<Courses> courses = registrationService.coursesRegisteredTo(user_name);
            printWriter.write(mapper.writeValueAsString(courses));
            resp.setStatus(200);

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


    protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        System.out.println(req.getAttribute("filtered"));
        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("application/json");


        String course_code = req.getParameter("course_code");
        String user_name = req.getParameter("user_name");


        try {
            registrationService.registerForClass(course_code,user_name);
            RegisteredCourseDTO dto = new RegisteredCourseDTO(user_name, course_code);
            respWriter.write(mapper.writeValueAsString(dto));
            resp.setStatus(200);
        } catch (InvalidRequestException e) {
            e.printStackTrace();
            resp.setStatus(400); // 400 - Bad Request
            ErrorResponse errResp = new ErrorResponse(400, e.getMessage());
            respWriter.write(mapper.writeValueAsString(errResp));
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
        }
    }


    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
        System.out.println(req.getAttribute("filtered"));
        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("application/json");


        String course_code = req.getParameter("course_code");
        String user_name = req.getParameter("user_name");

        try {
            registrationService.unregisterFromCourse(course_code,user_name);
            respWriter.write(mapper.writeValueAsString("Deleted: "+ "Successfully"));
            resp.setStatus(204);
        } catch (InvalidRequestException e) {
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
