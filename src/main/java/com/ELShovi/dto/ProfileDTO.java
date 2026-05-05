package com.ELShovi.dto;

import lombok.Data;

@Data
public class ProfileDTO {
    private String email;
    private String userName;
    private String fullName;
    private String password; // opcional
}
