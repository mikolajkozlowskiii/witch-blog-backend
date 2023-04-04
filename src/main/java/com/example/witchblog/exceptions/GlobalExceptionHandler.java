package com.example.witchblog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler({UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseStatusException handleResourceConflictException(RuntimeException ex) {
        return new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage(), ex);
    }

    @ResponseBody
    @ExceptionHandler({UsernameNotFoundException.class,
                       RoleNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseStatusException handleUsernameNotFoundException(RuntimeException ex) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
    }

    @ResponseBody
    @ExceptionHandler({AppException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseStatusException handleAppException(RuntimeException ex) {
        return new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage(), ex);
    }


}



