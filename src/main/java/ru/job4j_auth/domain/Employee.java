package ru.job4j_auth.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String surname;
    private long itn;

    @Column(name = "hiring_date")
    private Timestamp hiringDate;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Person> personSet;

    public static Employee of(String name, String surname, long itn) {
        Employee employee = new Employee();
        employee.name = name;
        employee.surname = surname;
        employee.itn = itn;
        employee.hiringDate = new Timestamp(System.currentTimeMillis());
        employee.personSet = new HashSet<>();
        return employee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public long getItn() {
        return itn;
    }

    public void setItn(long itn) {
        this.itn = itn;
    }

    public Timestamp getHiringDate() {
        return hiringDate;
    }

    public void setHiringDate(Timestamp hiringDate) {
        this.hiringDate = hiringDate;
    }

    public Set<Person> getPersonSet() {
        return personSet;
    }

    public void setPersonSet(Set<Person> personSet) {
        this.personSet = personSet;
    }

    public void addPerson(Person person) {
        this.personSet.add(person);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
