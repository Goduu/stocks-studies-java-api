package com.nelioalves.workshopmongo.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	private String name;
	
	private String email;

	@JsonIgnore private String hashedpw;

	// @DBRef(lazy = true)
	// private List<Post> posts = new ArrayList<>();


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@JsonIgnore
	public boolean isEmpty() {
		return this.email == null || "".equals(this.getEmail());
	}
}
