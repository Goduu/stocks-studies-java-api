package com.goduu.stocksstudies.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
@CompoundIndex(name = "user_ident_indx", def = "{'userId': 1, 'identifier': 1}")
public class Grid implements Serializable{

	private static final long serialVersionUID = 1L;

    private String id;
    // @DBRef(lazy = true)
    @NotNull
	private String userId;
    
    @NotNull
    private String identifier;

    private List<Map<String, Object>> gridElements = new ArrayList<>();

    private Boolean active;

    
}
