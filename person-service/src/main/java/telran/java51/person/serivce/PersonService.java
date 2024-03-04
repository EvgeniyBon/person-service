package telran.java51.person.serivce;

import java.util.List;

import telran.java51.person.dto.AddressDto;
import telran.java51.person.dto.PersonDto;

public interface PersonService {
	Boolean addPerson(PersonDto personDto);

	PersonDto findPersonById(Integer id);

	List<PersonDto> findPersonsByCity(String city);

	List<PersonDto> findPersonsByAges(Integer from, Integer to);
	
	PersonDto personNameUpdate (Integer id, String name);

	List<PersonDto> findPersonsByName(String name);

	PersonDto personAddressUpdate(Integer id, AddressDto addressDto);

	PersonDto deletePerson(Integer id);
}
