package com.simpletodo.todobackend.repository;

import com.simpletodo.todobackend.model.Task;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends MongoRepository<Task, ObjectId> {

    List<Task> findByUser(String user);
}

