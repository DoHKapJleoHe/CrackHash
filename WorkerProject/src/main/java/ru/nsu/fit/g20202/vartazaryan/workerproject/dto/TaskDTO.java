package ru.nsu.fit.g20202.vartazaryan.workerproject.dto;

import lombok.Data;

@Data
public class TaskDTO
{
    private String ticketID;
    private int start;
    private int finish;
    private int maxLen;
    private String hash;
}
