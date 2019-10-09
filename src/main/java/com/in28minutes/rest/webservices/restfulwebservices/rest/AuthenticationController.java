package com.in28minutes.rest.webservices.restfulwebservices.rest;

import com.in28minutes.rest.webservices.restfulwebservices.domain.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AuthenticationController {

    @GetMapping("/basic-auth")
    public Authentication authenticate() {
        return new Authentication("You are authenticated!");
    }
}
