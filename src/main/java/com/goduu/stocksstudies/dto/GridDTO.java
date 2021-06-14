package com.goduu.stocksstudies.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.goduu.stocksstudies.models.Grid;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GridDTO implements Serializable {

  private String id;
  // @DBRef(lazy = true)
  @NotNull(message = "Error - grid not related to a user")
  @Size(min = 3, message = "`name` must be at least 3 characters long")
  private String userId;

  @NotNull(message = "Error - grid not related to a identifier")
  private String identifier;

  private List<Map<String, Object>> gridElements = new ArrayList<>();

  private Boolean active;

  public GridDTO(Grid obj) {
    id = obj.getId();
    userId = obj.getUserId();
    identifier = obj.getIdentifier();
    gridElements = obj.getGridElements();
    active = obj.getActive();
  }

}
