package telran.java51.person.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import telran.java51.person.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {
List<Person> findByAddressCity(String city);
List<Person>findByBirthDateBetween(LocalDate from, LocalDate to);
List<Person> findPersonByName(String name);
}
