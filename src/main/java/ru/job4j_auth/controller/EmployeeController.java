package ru.job4j_auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j_auth.domain.Employee;

import ru.job4j_auth.domain.Person;
import ru.job4j_auth.repository.EmployeeRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private RestTemplate rest;
    private static final String API = "http://localhost:8080/person/";
    private static final String API_ID = "http://localhost:8080/person/{id}";
    private final EmployeeRepository repository;

    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public Set<Employee> findAll() {
        Set<Employee> rsl = new HashSet<>();
        List<Person> persons = rest.exchange(
                API,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() { }
        ).getBody();
        for (Person person : persons) {
            rsl.add(person.getEmployee());
            System.out.println(person.getEmployee().getName());
        }
        return rsl;
    }

}