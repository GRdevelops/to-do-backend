package com.simpletodo.todobackend.controller;

import com.simpletodo.todobackend.model.Task;
import com.simpletodo.todobackend.repository.TaskRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8080", "https://myquicklist.vercel.app", "https://my-quick-list-backend-5a463dab0825.herokuapp.com"})
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/tasks")
    public Task newTask(@RequestBody Task newTask) {
        return taskRepository.save(newTask);
    }

    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @GetMapping("/tasks/user/{user}")
    public List<Task> getUserTasks(@PathVariable String user) {
        return taskRepository.findByUser(user);
    }

    @GetMapping("/tasks/{id}")
    public Task getSingleTask(@PathVariable String id) {
        return taskRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
    }

    @PutMapping("/tasks/{id}")
    public Task updateTask(@RequestBody Task newTask, @PathVariable String id) {
        return taskRepository.findById(new ObjectId(id))
                .map(task -> {
                    task.setContent(newTask.getContent());
                    return taskRepository.save(task);
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
    }

    @DeleteMapping("/tasks/{id}")
    public String deleteTask(@PathVariable String id) {
        if(!taskRepository.existsById(new ObjectId(id))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        }
        taskRepository.deleteById(new ObjectId(id));
        return "Task with id " + id + " has been deleted successfully.";
    }

}
