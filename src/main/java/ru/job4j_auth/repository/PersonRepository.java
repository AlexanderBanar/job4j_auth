package ru.job4j_auth.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j_auth.domain.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {
}
