package ru.job4j_auth.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j_auth.domain.Employee;

import ru.job4j_auth.domain.Person;
import ru.job4j_auth.repository.EmployeeRepository;
import ru.job4j_auth.repository.PersonRepository;

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
    private final EmployeeRepository employeeRepository;
    private final PersonRepository personRepository;

    public EmployeeController(EmployeeRepository empRepo, PersonRepository perRepo) {
        this.employeeRepository = empRepo;
        this.personRepository = perRepo;
    }

    @GetMapping("/")
    public List<Employee> findAll() {
        return StreamSupport.stream(
                this.employeeRepository.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    @PostMapping("/{id}")
    public ResponseEntity<Person> create(@PathVariable int id, @RequestParam String login,
                                         @RequestParam String password) {
        Person person = Person.of(login, password);
        Optional employeeOpt = employeeRepository.findById(id);
        if (employeeOpt.isPresent()) {
            Employee employee = (Employee) employeeOpt.get();
            Person rsl = rest.postForObject(API, person, Person.class);
            personRepository.setEmp(rsl.getId(), employee);
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

    @PutMapping("/{pId}/{eId}")
    public ResponseEntity<Void> update(@PathVariable int pId,
                                       @PathVariable int eId,
                                       @RequestParam String login,
                                       @RequestParam String password) {
        Person person = Person.of(login, password);
        person.setId(pId);
        rest.put(API, person);
        Optional employeeOpt = employeeRepository.findById(eId);
        if (employeeOpt.isPresent()) {
            Employee employee = (Employee) employeeOpt.get();
            personRepository.setEmp(pId, employee);
            return new ResponseEntity<>(
                    null,
                    HttpStatus.OK
            );
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