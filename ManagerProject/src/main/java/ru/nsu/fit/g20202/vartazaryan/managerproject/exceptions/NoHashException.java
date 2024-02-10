package ru.nsu.fit.g20202.vartazaryan.managerproject.exceptions;

public class NoHashException extends RuntimeException
{
    public NoHashException()
    {
        super("Request does not contain hash!");
    }
}
