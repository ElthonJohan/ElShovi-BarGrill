package com.ELShovi.dto;

import com.ELShovi.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;


//import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer idUser;
    @NotNull
    @Email
    private String email;
    @NotNull
    @Size(min = 5, max = 50)
    private String userName;
    @NotNull
    @Size(min = 8, max = 50)
    private String password;
    @Size(min = 5, max = 100)
    private String fullName;
    @NotNull
    private boolean active;
    private LocalDateTime createdAt=LocalDateTime.now();
    @NotNull
    private List<Role> roles;
}
