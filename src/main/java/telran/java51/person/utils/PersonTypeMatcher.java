package telran.java51.person.utils;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import telran.java51.person.dto.ChildDto;
import telran.java51.person.dto.EmployeeDto;
import telran.java51.person.dto.PersonDto;
import telran.java51.person.model.Child;
import telran.java51.person.model.Employee;
import telran.java51.person.model.Person;

@Component
@RequiredArgsConstructor
public class PersonTypeMatcher {
	final ModelMapper mapper;

	public Person personTypeMatch(PersonDto personDto) {
		if (personDto instanceof EmployeeDto employeeDto) {
			return mapper.map(personDto, Employee.class);
		}
		if (personDto instanceof ChildDto) {
			return mapper.map(personDto, Child.class);
		}
		return mapper.map(personDto, Person.class);
	}

	public PersonDto personDtoTypeMatch(Person person) {
		if (person instanceof Employee) {
			return mapper.map(person, EmployeeDto.class);
		}
		if (person instanceof Child) {
			return mapper.map(person, ChildDto.class);
		}

		return mapper.map(person, PersonDto.class);
	}
}
