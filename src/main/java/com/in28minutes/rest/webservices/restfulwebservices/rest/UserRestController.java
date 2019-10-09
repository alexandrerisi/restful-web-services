package com.in28minutes.rest.webservices.restfulwebservices.rest;

import com.in28minutes.rest.webservices.restfulwebservices.domain.User;
import com.in28minutes.rest.webservices.restfulwebservices.exception.DuplicateUserException;
import com.in28minutes.rest.webservices.restfulwebservices.service.TodoUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/users/control")
public class UserRestController {

    @Autowired
    private TodoUserDetailsService todoUserDetailsService;

    @PostMapping
    public ResponseEntity createUser(@RequestBody User user) {
        if (user.getUsername() != null && user.getPassword() != null && user.getId() == 0) {
            if (!todoUserDetailsService.isUserTaken(user.getUsername())) {
                user = todoUserDetailsService.save(user);
                var responseUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                        .buildAndExpand(user.getUsername()).toUri();
                return ResponseEntity.created(responseUri).build();
            } else {
                throw new DuplicateUserException("The username is already taken!");
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{username}")
    public User retrieveUser(@PathVariable String username) {
        return (User) todoUserDetailsService.loadUserByUsername(username);
    }
}
