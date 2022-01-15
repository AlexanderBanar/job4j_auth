package ru.job4j_auth.service;

import org.springframework.stereotype.Service;
import ru.job4j_auth.domain.Person;
import ru.job4j_auth.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAllPersons() {
        List<Person> list = new ArrayList<>();
        this.personRepository.findAll().forEach(list::add);
        return list;
    }

    public Optional<Person> findPersonById(int id) {
        return this.personRepository.findById(id);
    }

    public Person save(Person person) {
        return this.personRepository.save(person);
    }

    public void deletePerson(Person person) {
        this.personRepository.delete(person);
    }
}