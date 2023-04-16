package com.example.witchblog.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@AllArgsConstructor
@ToString
public class UserResponse {
    private String email;
    private String firstName;
    private String lastName;
}
