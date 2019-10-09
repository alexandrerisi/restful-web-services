package com.in28minutes.rest.webservices.restfulwebservices.rest;

import com.in28minutes.rest.webservices.restfulwebservices.domain.Todo;
import com.in28minutes.rest.webservices.restfulwebservices.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/users")
public class TodoRestController {

    @Autowired
    private TodoService service;

    @GetMapping("{username}/todos")
    public Collection<Todo> getToDos(@PathVariable String username) {
        return service.getTodosForUser(username);
    }

    @GetMapping("/{username}/todos/{id}")
    public Todo getTodo(@PathVariable String username, @PathVariable int id) {
        return service.getById(id).orElse(null);
    }

    @DeleteMapping("/{username}/todos/{id}")
    public ResponseEntity deleteTodo(@PathVariable String username, @PathVariable int id) {
        var todo = service.getByUsernameAndId(username, id);
        if (todo.isPresent()) {
            service.deleteTodo(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{username}/todos")
    public ResponseEntity updateTodo(@PathVariable String username, @RequestBody Todo todo) {
        var optional = service.getByUsernameAndId(username, todo.getId());
        if (optional.isPresent()) {
            service.saveTodo(todo);
            return ResponseEntity.accepted().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{username}/todos")
    public ResponseEntity<Void> createTodo(@PathVariable String username, @RequestBody Todo todo) {
        if (todo.getId() <= 0) {
            todo.setUsername(username);
            var createdTodo = service.saveTodo(todo);
            var responseUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}")
                    .buildAndExpand(createdTodo.getId()).toUri();
            return ResponseEntity.created(responseUri).build();
        }
        return ResponseEntity.badRequest().build();
    }
}
