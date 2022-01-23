package ru.job4j_auth.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j_auth.domain.Employee;

import ru.job4j_auth.domain.Person;
import ru.job4j_auth.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
    public List<Employee> findAll() {
        return StreamSupport.stream(
                this.repository.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    @PostMapping("/{id}")
    public ResponseEntity<Person> create(@PathVariable int id, @RequestParam String login,
                                         @RequestParam String password) {
        Person person = Person.of(login, password);
        Optional employeeOpt = repository.findById(id);
        if (employeeOpt.isPresent()) {
            Employee employee = (Employee) employeeOpt.get();
            Person rsl = rest.postForObject(API, person, Person.class);
            employee.addPerson(person);
            repository.save(employee);
            return new ResponseEntity<>(
                    rsl,
                    HttpStatus.CREATED
            );
        } else {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody List<Person> persons) {
        Optional employeeOpt = repository.findById(id);
        if (employeeOpt.isPresent()) {
            Employee employee = (Employee) employeeOpt.get();
            for (Person person : persons) {
                person.setEmployee(employee);
                rest.put(API, person);
            }
            return ResponseEntity.ok().build();
        } else {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        rest.delete(API_ID, id);
        return ResponseEntity.ok().build();
    }
}