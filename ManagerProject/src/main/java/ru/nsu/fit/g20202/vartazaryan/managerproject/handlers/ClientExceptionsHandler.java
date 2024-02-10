package ru.nsu.fit.g20202.vartazaryan.managerproject.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.nsu.fit.g20202.vartazaryan.managerproject.exceptions.NoHashException;
import ru.nsu.fit.g20202.vartazaryan.managerproject.exceptions.NoMaxLengthException;

@RestControllerAdvice
public class ClientExceptionsHandler
{
    @ExceptionHandler(value = {NoHashException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String HashWasNotProvidedExceptionHandler(NoHashException exception)
    {
        return exception.getMessage();
    }

    @ExceptionHandler(value = {NoMaxLengthException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String MaxLengthWasNotProvided(NoMaxLengthException exception)
    {
        return exception.getMessage();
    }
}
