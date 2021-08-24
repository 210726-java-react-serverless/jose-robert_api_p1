package com.Revature.StudentManagementApp.web.servlets;

import com.Revature.StudentManagementApp.dataSource.documents.Faculty;
import com.Revature.StudentManagementApp.services.FacultyService;
import com.Revature.StudentManagementApp.util.exceptions.AuthenticationException;
import com.Revature.StudentManagementApp.web.dtos.Credentials;
import com.Revature.StudentManagementApp.web.dtos.ErrorResponse;
import com.Revature.StudentManagementApp.web.dtos.Principal;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class FacultyAuthServlet extends HttpServlet {

    private final FacultyService facultyService;
    private final ObjectMapper mapper;

    public FacultyAuthServlet(FacultyService facultyService, ObjectMapper mapper) {
        this.facultyService = facultyService;
        this.mapper = mapper;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("application/json");

        try {
            Credentials credentials = mapper.readValue(req.getInputStream(), Credentials.class);
            Faculty faculty = facultyService.login(credentials.getUsername(), credentials.getPassword());
            Principal principal = new Principal(faculty);

            String payload = mapper.writeValueAsString(principal);
            respWriter.write(payload);

            HttpSession session = req.getSession();
            session.setAttribute("auth-user", principal);
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