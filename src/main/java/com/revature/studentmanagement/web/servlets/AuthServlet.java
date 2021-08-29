package com.revature.studentmanagement.web.servlets;

import com.revature.studentmanagement.datasource.documents.Student;
import com.revature.studentmanagement.services.StudentService;
import com.revature.studentmanagement.util.exceptions.AuthenticationException;
import com.revature.studentmanagement.web.dtos.Credentials;
import com.revature.studentmanagement.web.dtos.ErrorResponse;
import com.revature.studentmanagement.web.dtos.Principal;
import com.revature.studentmanagement.web.util.security.TokenGenerator;
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
    private final TokenGenerator tokenGenerator;

    public AuthServlet(StudentService studentService, ObjectMapper mapper, TokenGenerator tokenGenerator) {
        this.studentService = studentService;
        this.mapper = mapper;
        this.tokenGenerator = tokenGenerator;
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

            String token = tokenGenerator.createToken(principal);
            resp.setHeader(tokenGenerator.getJwtConfig().getHeader(), token);

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
