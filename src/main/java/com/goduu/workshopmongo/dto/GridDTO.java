package com.goduu.workshopmongo.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.goduu.workshopmongo.models.Grid;
import com.goduu.workshopmongo.models.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GridDTO implements Serializable{

    private String id;
    // @DBRef(lazy = true)
    @NotNull(message = "Error - grid not related to a user")
  	@Size(min = 3, message = "`name` must be at least 3 characters long")
	private User user;

    @NotNull(message = "Error - grid not related to a identifier")
    private String identifier;

    private List<Map<String, Object>> gridElements = new ArrayList<>();

    public GridDTO(Grid obj) {
		id = obj.getId();
		user = obj.getUser();
		identifier = obj.getIdentifier();
        gridElements = obj.getGridElements();
	}

    
}
