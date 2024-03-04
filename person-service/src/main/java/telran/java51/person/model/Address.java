package telran.java51.person.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2172984761917024412L;
	String city;
	String street;
	Integer building;
}
