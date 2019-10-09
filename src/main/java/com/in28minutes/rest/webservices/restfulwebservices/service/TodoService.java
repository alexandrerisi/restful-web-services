package com.in28minutes.rest.webservices.restfulwebservices.service;

import com.in28minutes.rest.webservices.restfulwebservices.domain.Todo;
import com.in28minutes.rest.webservices.restfulwebservices.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository repository;

    public Collection<Todo> getTodosForUser(String username) {
        return repository.findAllByUsername(username);
    }

    public Todo saveTodo(Todo todo) {
        return repository.save(todo);
    }

    public Optional<Todo> getById(int id) {
        return repository.findById(id);
    }

    public Optional<Todo> getByUsernameAndId(String username, int id) {
        return repository.findByUsernameAndId(username, id);
    }

    public void deleteTodo(int id) {
        repository.deleteById(id);
    }
}
