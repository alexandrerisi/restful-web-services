package com.in28minutes.rest.webservices.restfulwebservices.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/welcome")
@RestController
public class WelcomeRestController {

    @GetMapping
    public Welcome welcome() {
        return new Welcome("Welcome ABC!");
    }

    @GetMapping("/{username}")
    public Welcome welcomeForUser(@PathVariable String username) {
        return new Welcome("Welcome " + username);
    }

    @AllArgsConstructor
    @Data
    class Welcome {
        String message;
    }
}
