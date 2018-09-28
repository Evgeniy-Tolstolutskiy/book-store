package com.tolstolutskyi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginData {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
