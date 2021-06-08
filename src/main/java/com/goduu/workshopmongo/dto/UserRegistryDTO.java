package com.goduu.workshopmongo.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistryDTO {

  @NotNull(message = "`name` field is mandatory")
  @Size(min = 3, message = "`name` must be at least 3 characters long")
  private String name;

  @NotNull(message = "`email` field is mandatory")
  @Email(message = "`email` must be an well-formed email address")
  private String email;

  @NotNull(message = "`password` field is mandatory")
  @Size(min = 8, message = "`password` must be at least 8 characters long")
  private String password;

}
