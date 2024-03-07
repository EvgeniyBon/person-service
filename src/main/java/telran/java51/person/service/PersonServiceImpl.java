package telran.java51.person.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.java51.person.dao.PersonRepository;
import telran.java51.person.dto.AddressDto;
import telran.java51.person.dto.ChildDto;
import telran.java51.person.dto.CityPopulationDto;
import telran.java51.person.dto.EmployeeDto;
import telran.java51.person.dto.PersonDto;
import telran.java51.person.dto.exceptions.PersonNotFoundException;
import telran.java51.person.model.Address;
import telran.java51.person.model.Child;
import telran.java51.person.model.Employee;
import telran.java51.person.model.Person;
import telran.java51.person.utils.PersonTypeMatcher;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService,CommandLineRunner {

	final PersonRepository personRepository;
	final ModelMapper modelMapper;
	final PersonTypeMatcher matcher;

	@Transactional
	@Override
	public Boolean addPerson(PersonDto personDto) {
		if (personRepository.existsById(personDto.getId())) {
			return false;
		}
		Person matchedPerson=matcher.personTypeMatch(personDto);
		personRepository.save(matchedPerson);
		return true;
	}

	@Override
	public PersonDto findPersonById(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		return matcher.personDtoTypeMatch(person);
	}

	@Transactional
	@Override
	public PersonDto removePerson(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException());
		personRepository.delete(person);
		return matcher.personDtoTypeMatch(person);
	}

	@Transactional
	@Override
	public PersonDto updatePersonName(Integer id, String name) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException());
		person.setName(name);
//		personRepository.save(person);
		return matcher.personDtoTypeMatch(person);
	}

	@Transactional
	@Override
	public PersonDto updatePersonAddress(Integer id, AddressDto addressDto) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException());
		person.setAddress(modelMapper.map(addressDto, Address.class));
//		personRepository.save(person);
		return matcher.personDtoTypeMatch(person);
	}

	@Transactional(readOnly = true)
	@Override
	public Iterable<PersonDto> findPersonsByCity(String city) {
		return personRepository.findByAddressCityIgnoreCase(city).map(p -> matcher.personDtoTypeMatch(p))
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	@Override
	public Iterable<PersonDto> findPersonsByName(String name) {
		return personRepository.findByNameIgnoreCase(name).map(p -> matcher.personDtoTypeMatch(p))
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	@Override
	public Iterable<PersonDto> findPersonsBetweenAge(Integer minAge, Integer maxAge) {
		LocalDate from = LocalDate.now().minusYears(maxAge);
		LocalDate to = LocalDate.now().minusYears(minAge);
		return personRepository.findByBirthDateBetween(from, to).map(p -> matcher.personDtoTypeMatch(p))
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	@Override
	public Iterable<CityPopulationDto> getCitiesPopulation() {
		return personRepository.getCitiesPopulation();
	}
	
@Transactional
	@Override
	public void run(String... args) throws Exception {
		if (personRepository.count()==0) {
			Person person = new Person(1000,"John",LocalDate.of(1985, 3, 11),
					new Address("Tel Aviv", "Ben Gvirol",81));
			Child child = new Child(2000,"Mosche",LocalDate.of(2018, 7, 5),
					new Address("Ashkelon", "Bar Kohval",21),"Shalom");
			Employee employee = new Employee(3000,"Sarah",LocalDate.of(1995, 11, 23),
					new Address("Rehevot", "Herzel",7),"Motorola",20_000);
			personRepository.save(person);
			personRepository.save(child);
			personRepository.save(employee);
		}
	}

@Override
@Transactional(readOnly = true)
public List<ChildDto> getChildren() {
	return personRepository.getChildren().stream().map(p->modelMapper.map(p, ChildDto.class)).toList();

}

@Override
@Transactional(readOnly = true)
public Iterable<EmployeeDto> getEmployeesBySalary(Integer min, Integer max) {
	return personRepository.getEmployeeBySalary(min, max).stream().map(e->modelMapper.map(e, EmployeeDto.class)).toList();
}
}