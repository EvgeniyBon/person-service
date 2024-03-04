package telran.java51.person.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java51.person.dto.AddressDto;
import telran.java51.person.dto.PersonDto;
import telran.java51.person.serivce.PersonService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/person")
public class PersonController {
	final PersonService personService;

	@PostMapping
	public Boolean addPerson(@RequestBody PersonDto personDto) {
		return personService.addPerson(personDto);
	}

	@GetMapping("/{id}")
	public PersonDto getPersonById(@PathVariable Integer id) {
	return	personService.findPersonById(id);
	}
	
	@GetMapping("/city/{city}")
	List<PersonDto> findPersonByCity(@PathVariable String city) {
		return personService.findPersonsByCity(city);
	}
	
	@GetMapping("/ages/{from}/{to}")
	List<PersonDto>findPersonsByAges(@PathVariable Integer from, @PathVariable Integer to){
		return personService.findPersonsByAges(from,to);
	}
	
	@PutMapping("/{id}/name/{name}")
	public PersonDto personNameUpdate(@PathVariable Integer id,@PathVariable String name) {
		return personService.personNameUpdate(id, name);
	}
	
	@GetMapping("/name/{name}")
	public List<PersonDto> findPersonsByName(@PathVariable String name) {
		return personService.findPersonsByName(name);
	}
	
	@PutMapping("/{id}/address")
	public PersonDto personAddressUpdate(@PathVariable Integer id, @RequestBody AddressDto addressDto) {
	return	personService.personAddressUpdate(id,addressDto);
	}
	
	@DeleteMapping("/{id}")
	public PersonDto deletePerson(@PathVariable Integer id) {
		return personService.deletePerson(id);
	}
}
