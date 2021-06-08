package com.nelioalves.workshopmongo.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.nelioalves.workshopmongo.models.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	@NotNull(message = "`name` field is mandatory")
  	@Size(min = 3, message = "`name` must be at least 3 characters long")
	private String name;

	@NotNull(message = "`email` field is mandatory")
  	@Email(message = "`email` must be an well-formed email address")
	private String email;

	@NotNull(message = "`password` field is mandatory")
	@Size(min = 8, message = "`password` must be at least 8 characters long")
	private String password;
	
	public UserDTO(User obj) {
		id = obj.getId();
		name = obj.getName();
		email = obj.getEmail();
	}


}
