package com.revature.studentmanagement.web.servlets;

import com.revature.studentmanagement.datasource.documents.Faculty;
import com.revature.studentmanagement.services.FacultyService;
import com.revature.studentmanagement.util.exceptions.AuthenticationException;
import com.revature.studentmanagement.web.dtos.Credentials;
import com.revature.studentmanagement.web.dtos.ErrorResponse;
import com.revature.studentmanagement.web.dtos.Principal;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.studentmanagement.web.util.security.TokenGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class FacultyAuthServlet extends HttpServlet {

    private final FacultyService facultyService;
    private final ObjectMapper mapper;
    private final TokenGenerator tokenGenerator;

    public FacultyAuthServlet(FacultyService facultyService, ObjectMapper mapper, TokenGenerator tokenGenerator) {
        this.facultyService = facultyService;
        this.mapper = mapper;
        this.tokenGenerator = tokenGenerator;
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
