package com.example.witchblog.dto.users.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class UpdateUserRequest {
    @Size(min = 6, max = 40)
    private String password;
    @Size(max = 50)
    private String firstName;
    @Size(max = 50)
    private String lastName;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    private Date birthDate;
}
