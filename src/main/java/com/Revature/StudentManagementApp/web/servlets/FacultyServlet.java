package com.Revature.StudentManagementApp.web.servlets;

import com.Revature.StudentManagementApp.dataSource.documents.Faculty;
import com.Revature.StudentManagementApp.dataSource.documents.Student;
import com.Revature.StudentManagementApp.services.FacultyService;
import com.Revature.StudentManagementApp.util.exceptions.InvalidRequestException;
import com.Revature.StudentManagementApp.web.dtos.ErrorResponse;
import com.Revature.StudentManagementApp.web.dtos.Principal;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class FacultyServlet extends HttpServlet {

    private final FacultyService facultyService;
    private final ObjectMapper mapper;


    public FacultyServlet(FacultyService facultyService, ObjectMapper mapper) {
        this.facultyService = facultyService;
        this.mapper = mapper;
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("<h1>/Faculty Servlet is working works!</h1>");
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getAttribute("filtered"));
        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("application/json");

        Principal requestingUser = (Principal) req.getAttribute("principal");

        try {
            Faculty newFaculty = mapper.readValue(req.getInputStream(), Faculty.class);
            Principal principal = new Principal(facultyService.register(newFaculty));
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
