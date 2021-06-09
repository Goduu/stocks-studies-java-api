package com.goduu.stocksstudies.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
@Document
public class Grid implements Serializable{

	private static final long serialVersionUID = 1L;

    @Id
    private String id;
    // @DBRef(lazy = true)
	private String userId;

    private String identifier;

    private List<Map<String, Object>> gridElements = new ArrayList<>();

    private Boolean active;

    
}
