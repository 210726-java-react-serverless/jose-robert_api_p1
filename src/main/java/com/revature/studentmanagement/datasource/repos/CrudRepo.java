package com.revature.studentmanagement.datasource.repos;

public interface CrudRepo<T> {

    T findById(String id);
    T save(T newResource);
    boolean update(T updateResource);
    boolean deleteById(String id);

}
