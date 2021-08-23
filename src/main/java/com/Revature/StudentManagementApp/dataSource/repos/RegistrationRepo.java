package com.Revature.StudentManagementApp.dataSource.repos;

import com.Revature.StudentManagementApp.dataSource.documents.Courses;
import com.Revature.StudentManagementApp.util.MongoConnection;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class RegistrationRepo {
    public List<Courses> getAllCourses() {
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
                e.printStackTrace();
            }

        }


        return courses;

    }


    public void addRegisteredCoursses(Courses c, String s){
        MongoConnection mc = MongoConnection.getInstance();
        MongoClient mongoClient = mc.getConnection();

        MongoDatabase p0 = mongoClient.getDatabase("project");
        MongoCollection<Document> usersCollection = p0.getCollection("registeredTo");

        Document classDoc = new Document("course_code", c.getCourse_code())
                .append("user_name", s);


        usersCollection.insertOne(classDoc);

    }


    public Courses findByCourseCode(String course_code){



        MongoConnection cm = MongoConnection.getInstance();
        MongoClient mongoClient = cm.getConnection();

        MongoDatabase p0 = mongoClient.getDatabase("project");
        MongoCollection<Document> usersCollection = p0.getCollection("courses");
        Document queryDoc = new Document("course_code", course_code);

        Document courseDoc = usersCollection.find(queryDoc).first();


        if(courseDoc == null){
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        Courses course = null;
        try {

            course = mapper.readValue(courseDoc.toJson(), Courses.class);
            return course;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return course;

    }

    public List<String> coursesRegisteredFor(String s){
        MongoConnection cm = MongoConnection.getInstance();
        MongoClient mongoClient = cm.getConnection();

        MongoDatabase p0 = mongoClient.getDatabase("project");
        MongoCollection<Document> usersCollection = p0.getCollection("registeredTo");
        Document queryDoc = new Document("user_name", s);
        MongoCursor<Document> courseDoc = usersCollection.find(queryDoc).iterator();
        List<String> courses = new ArrayList();

        while(courseDoc.hasNext())
        {
            Document docResults = courseDoc.next();
            String code = docResults.get("course_code").toString();
            courses.add(code);



        }


        return courses;





    }

    public void unRegisterCourse(String code, String username){
        MongoConnection mc = MongoConnection.getInstance();
        MongoClient mongoClient = mc.getConnection();

        MongoDatabase p0 = mongoClient.getDatabase("project");
        MongoCollection<Document> usersCoursesCollection = p0.getCollection("registeredTo");
        Document queryDoc = new Document("course_code", code).append("user_name", username);

        try{
            DeleteResult result = usersCoursesCollection.deleteOne(queryDoc);
            if (result.getDeletedCount() == 0){
                //TODO put logging here
                System.out.println("Removed: " + result);
            }else{
                //TODO put logging here
                System.out.println("Removed: " + result);
            }

        }catch(MongoException m)
        {
            System.out.println("user doesnt exist");
        }
    }


    public void unRegisterStudentsFromDeletedCourse(String course_code){
        MongoConnection mc = MongoConnection.getInstance();
        MongoClient mongoClient = mc.getConnection();

        MongoDatabase p0 = mongoClient.getDatabase("project");
        MongoCollection<Document> usersCoursesCollection = p0.getCollection("registeredTo");
        Document queryDoc = new Document("course_code", course_code);

        try{
            DeleteResult result = usersCoursesCollection.deleteMany(queryDoc);
            System.out.println("Removed: " + result);
        }catch(MongoException m)
        {
            System.out.println("user doesnt exist");
        }
    }



}
