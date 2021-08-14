package com.Revature.StudentManagementApp.dataSource.repos;

import com.Revature.StudentManagementApp.dataSource.documents.Courses;
import com.Revature.StudentManagementApp.util.MongoConnection;

import com.Revature.StudentManagementApp.util.exceptions.DataSourceException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class CourseRepo implements CrudRepo<Courses> {

    private static final MongoClient client = MongoConnection.getInstance().getConnection();

    @Override
    public Courses findById(String id) {
        try {
            MongoDatabase database = client.getDatabase("p0");
            MongoCollection<Document> courseCollection = database.getCollection("courses");

            Document queryDoc = new Document("course_code", id);
            Document resultDoc = courseCollection.find(queryDoc).first();

            if (resultDoc == null) {
                return null;
            }

            ObjectMapper mapper = new ObjectMapper();
            Courses course = mapper.readValue(resultDoc.toJson(), Courses.class);
            return course;

        } catch (Exception e) {
            throw new DataSourceException("An unexpected error occurred", e);
        }
    }

    @Override
    public Courses save(Courses course) {
        try {
            // TODO switch variables to environment variables
            MongoDatabase database = client.getDatabase("p0");
            MongoCollection<Document> courseCollection = database.getCollection("courses");

            Document courseDoc = new Document("course_name", course.getCourse_name())
                    .append("course_code", course.getCourse_code())
                    .append("course_date", course.getStart_date())
                    .append("end_date", course.getEnd_date());

            courseCollection.insertOne(courseDoc);

            return course;

        } catch (Exception e) {
            throw new DataSourceException("An unexpected error occurred", e);
        }
    }

    @Override
    public boolean update(Courses course) {
        try {
            MongoDatabase database = client.getDatabase("p0");
            MongoCollection<Document> courseCollection = database.getCollection("courses");

            Document queryDoc = new Document("course_code", course.getCourse_code());

            // TODO the rest of this logic
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }
}
