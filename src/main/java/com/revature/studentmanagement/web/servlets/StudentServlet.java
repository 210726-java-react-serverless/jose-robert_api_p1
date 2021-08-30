package com.revature.studentmanagement.web.servlets;

import com.revature.studentmanagement.datasource.documents.Student;
import com.revature.studentmanagement.services.StudentService;
import com.revature.studentmanagement.util.exceptions.InvalidRequestException;
import com.revature.studentmanagement.web.dtos.ErrorResponse;
import com.revature.studentmanagement.web.dtos.Principal;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class StudentServlet extends HttpServlet {

    private final StudentService studentService;
    private final ObjectMapper mapper;

    public StudentServlet(StudentService studentService, ObjectMapper mapper) {
        this.studentService = studentService;
        this.mapper = mapper;
    }

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println(req.getAttribute("filtered"));
//        PrintWriter printWriter = resp.getWriter();
//        resp.setContentType("application/json");
//
//        try{
//            List<Student> student;
//            if(a){
//                Allcourse = registrationService.listCoursesAvailable();
//            }else
//            {
//                Allcourse = registrationService.listCoursesOffered();
//            }
//            printWriter.write(mapper.writeValueAsString(Allcourse));
//
//
//            resp.setStatus(200);
//
//
//        }catch (DataSourceException rnfe) {
//            resp.setStatus(404);
//            ErrorResponse errResp = new ErrorResponse(404, rnfe.getMessage());
//            printWriter.write(mapper.writeValueAsString(errResp));
//        } catch (Exception e) {
//            e.printStackTrace();
//            resp.setStatus(500); // server's fault
//            ErrorResponse errResp = new ErrorResponse(500, "The server experienced an issue, please try again later.");
//            printWriter.write(mapper.writeValueAsString(errResp));
//        }
//
//
//
//
//
//
//    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getAttribute("filtered"));
        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("application/json");

        try {
            Student newStudent = mapper.readValue(req.getInputStream(), Student.class);
            Principal principal = new Principal(studentService.register(newStudent));
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
