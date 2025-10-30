package com.ELShovi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;


//import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private int id;
    @NotNull
    private String email;
    @NotNull
    private String username;
    private String fullName;
    private boolean active;
    private LocalDateTime createdAt;
    @NotNull
    private int roleId;
}
