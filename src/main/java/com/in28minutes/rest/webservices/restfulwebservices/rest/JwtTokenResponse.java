package com.in28minutes.rest.webservices.restfulwebservices.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class JwtTokenResponse implements Serializable {

    private static final long serialVersionUID = 8317676219297719109L;
    private final String token;
}
