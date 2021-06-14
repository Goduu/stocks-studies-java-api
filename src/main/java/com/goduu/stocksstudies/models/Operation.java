package com.goduu.stocksstudies.models;

import java.io.Serializable;
import java.util.Date;

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
@Document(collection = "operation")
public class Operation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	private String userId;
	
	private String asset;

	//1=buy, 2 = sell, 3 = dividend
	private int operation;

	private double value;

	private double shares;

	private long date;

}
