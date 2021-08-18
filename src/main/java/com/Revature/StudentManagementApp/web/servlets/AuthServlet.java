package com.Revature.StudentManagementApp.web.servlets;

import com.Revature.StudentManagementApp.dataSource.documents.Student;
import com.Revature.StudentManagementApp.services.StudentService;
import com.Revature.StudentManagementApp.util.exceptions.AuthenticationException;
import com.Revature.StudentManagementApp.web.dtos.Credentials;
import com.Revature.StudentManagementApp.web.dtos.ErrorResponse;
import com.Revature.StudentManagementApp.web.dtos.Principal;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthServlet extends HttpServlet {

    private final StudentService studentService;
    private final ObjectMapper mapper;

    public AuthServlet(StudentService studentService, ObjectMapper mapper) {
        this.studentService = studentService;
        this.mapper = mapper;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("application/json");

        try {
            Credentials credential = mapper.readValue(req.getInputStream(), Credentials.class);
            Student student = studentService.login(credential.getUsername(), credential.getPassword());
            Principal principal = new Principal(student);

            String payload = mapper.writeValueAsString(principal);
            respWriter.write(payload);
        } catch (AuthenticationException e) {
            resp.setStatus(401);
            ErrorResponse errResp = new ErrorResponse(401, e.getMessage());
            respWriter.write(mapper.writeValueAsString(errResp));
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
            ErrorResponse errResp = new ErrorResponse(500, e.getMessage());
            respWriter.write(mapper.writeValueAsString(errResp));
        }
    }
}
