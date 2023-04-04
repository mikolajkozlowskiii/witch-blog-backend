package com.example.witchblog.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class UserResponse {
    private String username;

    @Override
    public String toString() {
        return "UserResponse{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    private String email;
    private String firstName;
    private String lastName;
}
