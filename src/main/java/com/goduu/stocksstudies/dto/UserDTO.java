package com.goduu.stocksstudies.dto;

import java.io.Serializable;
import java.util.Map;

import com.goduu.stocksstudies.models.User;

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

	private String name;

	private String email;

	private String password;

	private Map<String, String> avatar;
	
	public UserDTO(User obj) {
		id = obj.getId();
		name = obj.getName();
		email = obj.getEmail();
		avatar = obj.getAvatar();
	}


}
