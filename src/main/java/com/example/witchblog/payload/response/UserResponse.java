package com.example.witchblog.payload.response;

import lombok.Builder;
@Builder
public class UserResponse {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
}
