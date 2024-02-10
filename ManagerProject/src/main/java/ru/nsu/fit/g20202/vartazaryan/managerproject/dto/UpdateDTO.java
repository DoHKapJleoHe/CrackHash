package ru.nsu.fit.g20202.vartazaryan.managerproject.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateDTO
{
    private String ticketID;
    private List<String> result;
}
