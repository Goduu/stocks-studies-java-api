package com.nelioalves.workshopmongo.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class AuthDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	
	private String password;
	
}
