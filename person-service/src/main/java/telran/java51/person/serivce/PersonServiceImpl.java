package telran.java51.person.serivce;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java51.person.dao.PersonRepository;
import telran.java51.person.dto.AddressDto;
import telran.java51.person.dto.PersonDto;
import telran.java51.person.dto.exceptions.PersonNotFoundException;
import telran.java51.person.model.Person;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
	final PersonRepository personRepository;
	final ModelMapper modelMapper;

	@Override
	public Boolean addPerson(PersonDto personDto) {
		if (personRepository.existsById(personDto.getId())) {
			return false;
		}
		personRepository.save(modelMapper.map(personDto, Person.class));
		return true;
	}

	@Override
	public PersonDto findPersonById(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException());
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public List<PersonDto> findPersonsByCity(String city) {
		return personRepository.findByAddressCity(city).stream().map(p -> modelMapper.map(p, PersonDto.class)).toList();
	}

	@Override
	public List<PersonDto> findPersonsByAges(Integer from, Integer to) {
		LocalDate fromDate = LocalDate.now().minusYears(to + 1); // include 'to' (example from 20 to 30 includes)
		LocalDate toDate = LocalDate.now().minusYears(from);
		return personRepository.findByBirthDateBetween(fromDate, toDate).stream()
				.map(p -> modelMapper.map(p, PersonDto.class)).toList();
	}

	@Override
	public PersonDto personNameUpdate(Integer id, String name) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException());
		if (name != null && !name.equals("")) {
			person.setName(name);
		}
		return modelMapper.map(personRepository.save(person), PersonDto.class);
	}

	@Override
	public List<PersonDto> findPersonsByName(String name) {
		List<Person> persons = personRepository.findPersonByName(name);
		return persons.stream().map(p -> modelMapper.map(p, PersonDto.class)).toList();
	}

	@Override
	public PersonDto personAddressUpdate(Integer id, AddressDto addressDto) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException());
		if (addressDto.getCity() != null && !addressDto.getCity().equals("")) {
			person.getAddress().setCity(addressDto.getCity());
		}
		if (addressDto.getBuilding() != null) {
			person.getAddress().setBuilding(addressDto.getBuilding());
		}
		if (addressDto.getStreet() != null && !addressDto.getStreet().equals("")) {
			person.getAddress().setStreet(addressDto.getStreet());
		}
		return modelMapper.map(personRepository.save(person), PersonDto.class);
	}

	@Override
	public PersonDto deletePerson(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException());
		personRepository.delete(person);
		return modelMapper.map(person, PersonDto.class);
	}
}
