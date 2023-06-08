package com.example.witchblog.dto.users.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@ToString
public class UserResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Date birthDate;
}
