package ru.job4j_auth.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j_auth.domain.Person;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Integer> {

    @Query("Select distinct p from Person p join fetch p.employee")
    List<Person> findAllPersons();
}
