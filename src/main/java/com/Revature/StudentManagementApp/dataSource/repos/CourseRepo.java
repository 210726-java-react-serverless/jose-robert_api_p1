package com.Revature.StudentManagementApp.dataSource.repos;

import com.Revature.StudentManagementApp.dataSource.documents.Courses;
import com.Revature.StudentManagementApp.util.MongoConnection;

import com.Revature.StudentManagementApp.util.exceptions.DataSourceException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

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
            MongoDatabase database = client.getDatabase("project");
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
        return false;
    }

    @Override
    public boolean deleteById(String id) {
        try {
            MongoDatabase database = client.getDatabase("project");
            MongoCollection<Document> courseCollection = database.getCollection("courses");

            Document queryDoc = new Document("course_Id", id);
            Document removeDoc = courseCollection.find(queryDoc).first();

            if (removeDoc == null) {
                return false;
            }

            courseCollection.deleteOne(removeDoc);
            return true;

        } catch (Exception e) {
            throw new DataSourceException("An unexpected error occurred", e);
        }
    }

    public boolean updateCourse(Courses course, String field, String newData) {
        try {
<<<<<<< HEAD
            MongoDatabase database = client.getDatabase("jose_project_0");
=======
            MongoDatabase database = client.getDatabase("project");
>>>>>>> a51c0abec488bd0cfcacbe4da61497e3aebdd392
            MongoCollection<Document> courseCollection = database.getCollection("courses");

            Document queryDoc = new Document("course_code", course.getCourse_code());
            Document returnDoc = courseCollection.find(queryDoc).first();

            if (returnDoc == null) {
                return false;
            }

            Document newDoc = new Document(field, newData);
            Document updateDoc = new Document("$set", newDoc);
            courseCollection.updateOne(returnDoc, updateDoc);

            return true;

        } catch (Exception e) {
            throw new DataSourceException("An unexpected error occurred", e);
        }
    }

    public List<Courses> getCourses() {
        try {
            MongoDatabase database = client.getDatabase("project");
            MongoCollection<Document> courseCollection = database.getCollection("courses");

            MongoCursor<Document> cursor = courseCollection.find().iterator();
            ObjectMapper mapper = new ObjectMapper();

            List<Courses> courses = new ArrayList<>();
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Courses course = mapper.readValue(doc.toJson(), Courses.class);
            }

            return courses;

        } catch (Exception e) {
            throw new DataSourceException("An unexpected error occurred", e);
        }
    }
}
