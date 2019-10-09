package com.in28minutes.rest.webservices.restfulwebservices.repository;

import com.in28minutes.rest.webservices.restfulwebservices.domain.Todo;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface TodoRepository extends CrudRepository<Todo, Integer> {

    Collection<Todo> findAllByUsername(String username);

    Optional<Todo> findByUsernameAndId(String username, int id);
}
