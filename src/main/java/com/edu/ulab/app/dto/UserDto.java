package com.edu.ulab.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String fullName;
    private String title;
    private int age;
}
