package com.revature.studentmanagement.web.servlets;

import com.revature.studentmanagement.datasource.documents.Courses;
import com.revature.studentmanagement.services.CourseService;
import com.revature.studentmanagement.services.RegistrationService;
import com.revature.studentmanagement.util.exceptions.DataSourceException;
import com.revature.studentmanagement.util.exceptions.InvalidRequestException;
import com.revature.studentmanagement.web.dtos.CourseDTO;
import com.revature.studentmanagement.web.dtos.CoursePrincipal;
import com.revature.studentmanagement.web.dtos.ErrorResponse;
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
        resp.setContentType("application/json");

        boolean a = Boolean.parseBoolean(req.getParameter("available"));
        try{
            List<Courses> Allcourse;
            if(a){
                Allcourse = registrationService.listCoursesAvailable();
            }else
            {
                Allcourse = registrationService.listCoursesOffered();
            }
            printWriter.write(mapper.writeValueAsString(Allcourse));


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



    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        System.out.println(req.getAttribute("filtered"));
        PrintWriter printWriter = resp.getWriter();
        resp.setContentType("application/json");

        String course_code = req.getParameter("course_code");
        try{
//            Courses deleteCourse = mapper.readValue(req.getInputStream(), Courses.class );

            if (courseService.isCourseValid(registrationService.findByCourseCode(course_code))){
                courseService.deleteCourse(course_code);
                registrationService.removeStudentsRegisteredToCourse(course_code);
                String payload = mapper.writeValueAsString(course_code);
                printWriter.write(payload);

            }else{
                courseService.deleteCourse(course_code);
                String payload = mapper.writeValueAsString(registrationService.findByCourseCode(course_code));
                printWriter.write(payload);
            }
            resp.setStatus(200);

        }catch (InvalidRequestException | MismatchedInputException e) {
            e.printStackTrace();
            resp.setStatus(400); // 400 - Bad Request
            ErrorResponse errResp = new ErrorResponse(400, e.getMessage());
            printWriter.write(mapper.writeValueAsString(errResp));
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
        }

    }


    //TODO add an update method


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
        System.out.println(req.getAttribute("filtered"));
        PrintWriter printWriter = resp.getWriter();
        resp.setContentType("application/json");




        try{
            CoursePrincipal course = mapper.readValue(req.getInputStream(), CoursePrincipal.class);

            courseService.updateCourse(registrationService.findByCourseCode(course.getCourse_code()), course.getField(), course.getUpdateTo());
            System.out.println(course);
            String payload = mapper.writeValueAsString(course);
            printWriter.write(payload);
            resp.setStatus(200);

        }catch (InvalidRequestException e) {
            e.printStackTrace();
            resp.setStatus(400); // 400 - Bad Request
            ErrorResponse errResp = new ErrorResponse(400, e.getMessage());
            printWriter.write(mapper.writeValueAsString(errResp));
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
        }


    }





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
