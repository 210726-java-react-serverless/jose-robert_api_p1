package com.revature.studentmanagement.datasource.repos;

import com.revature.studentmanagement.datasource.documents.Courses;
import com.revature.studentmanagement.util.MongoConnection;
import com.revature.studentmanagement.util.exceptions.DataSourceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class RegistrationRepo {

    private final Logger logger = LoggerFactory.getLogger(RegistrationRepo.class);

    //Gets all courses available for students
    public List<Courses> getAllCourses() {
        try {
            MongoConnection cm = MongoConnection.getInstance();
            MongoClient mongoClient = cm.getConnection();

            MongoDatabase p0 = mongoClient.getDatabase("project");
            MongoCollection<Document> courseCollection = p0.getCollection("courses");
            MongoCursor<Document> c = courseCollection.find().iterator();
            ObjectMapper mapper = new ObjectMapper();

            List<Courses> courses = new ArrayList();
            while(c.hasNext())
            {
                Document docResults = c.next();
                try {
                    Courses course = mapper.readValue(docResults.toJson(), Courses.class);
                    course.setCourse_id(docResults.get("_id").toString());
                    courses.add(course);

                } catch (JsonProcessingException e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }

            }
            return courses;

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataSourceException("An unexpected error occurred", e);
        }
    }


    //adds a course name and username to Db to keep track of courses students are registered to.
    public void addRegisteredCourses(Courses c, String s) {
        try {
            MongoConnection mc = MongoConnection.getInstance();
            MongoClient mongoClient = mc.getConnection();

            MongoDatabase p0 = mongoClient.getDatabase("project");
            MongoCollection<Document> usersCollection = p0.getCollection("registeredTo");

            Document classDoc = new Document("course_code", c.getCourse_code())
                    .append("user_name", s);

            System.out.println(classDoc);

            usersCollection.insertOne(classDoc);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataSourceException("An unexpected error occurred", e);
        }

    }

    // finds course by course code
    public Courses findByCourseCode(String course_code) {
        try {
            MongoConnection cm = MongoConnection.getInstance();
            MongoClient mongoClient = cm.getConnection();

            MongoDatabase p0 = mongoClient.getDatabase("project");
            MongoCollection<Document> usersCollection = p0.getCollection("courses");
            Document queryDoc = new Document("course_code", course_code);

            Document courseDoc = usersCollection.find(queryDoc).first();

            if (courseDoc == null) {
                return null;
            }

            ObjectMapper mapper = new ObjectMapper();
            Courses course = mapper.readValue(courseDoc.toJson(), Courses.class);
            course.setCourse_id(courseDoc.get("_id").toString());

            return course;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataSourceException("An unexpected error occurred", e);
        }
    }

    //gets all the courses that a student is registered for by user name
    public List<String> coursesRegisteredFor(String s) {
        try {
            MongoConnection cm = MongoConnection.getInstance();
            MongoClient mongoClient = cm.getConnection();

            MongoDatabase p0 = mongoClient.getDatabase("project");
            MongoCollection<Document> usersCollection = p0.getCollection("registeredTo");
            Document queryDoc = new Document("user_name", s);
            MongoCursor<Document> courseDoc = usersCollection.find(queryDoc).iterator();
            List<String> courses = new ArrayList();

            while(courseDoc.hasNext()) {
                Document docResults = courseDoc.next();
                String code = docResults.get("course_code").toString();
                courses.add(code);
            }

            return courses;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataSourceException("An unexpected error occurred", e);
        }

    }




    //unregisters a student by course.
    public void unRegisterCourse(String code, String username){
        try {
            MongoConnection mc = MongoConnection.getInstance();
            MongoClient mongoClient = mc.getConnection();

            MongoDatabase p0 = mongoClient.getDatabase("project");
            MongoCollection<Document> usersCoursesCollection = p0.getCollection("registeredTo");
            Document queryDoc = new Document("course_code", code).append("user_name", username);

            DeleteResult result = usersCoursesCollection.deleteOne(queryDoc);

            logger.info("Removed: " + result);
            System.out.println("Removed: " + result);

        } catch (MongoException m) {
            logger.error("User doesn't exist");

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataSourceException("An unexpected error occurred", e);
        }
    }



    ///unregisteres all students registered to a course that has beeen deleted by the database.
    public void unRegisterStudentsFromDeletedCourse(String course_code) {
        try {
            MongoConnection mc = MongoConnection.getInstance();
            MongoClient mongoClient = mc.getConnection();

            MongoDatabase p0 = mongoClient.getDatabase("project");
            MongoCollection<Document> usersCoursesCollection = p0.getCollection("registeredTo");
            Document queryDoc = new Document("course_code", course_code);

            DeleteResult result = usersCoursesCollection.deleteMany(queryDoc);
            System.out.println("Removed: " + result);
        } catch (MongoException m) {
            logger.error("User doesn't exist");
            throw new DataSourceException("An unexpected error occurred", m);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataSourceException("An unexpected error occurred", e);
        }
    }
}
