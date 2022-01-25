package ru.job4j_auth.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j_auth.domain.Employee;
import ru.job4j_auth.domain.Person;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Integer> {

    @Query("Select distinct p from Person p join fetch p.employee")
    List<Person> findAllPersons();

    @Transactional
    @Modifying
    @Query("Update Person p set p.employee = :empId where p.id = :pId")
    void setEmp(@Param("pId") int pId, @Param("empId") Employee employee);
}
