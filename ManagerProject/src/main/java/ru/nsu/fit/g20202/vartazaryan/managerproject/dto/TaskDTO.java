package ru.nsu.fit.g20202.vartazaryan.managerproject.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskDTO
{
    private String ticketID;
    private long start;
    private long checkAmount;
    private int maxLen;
    private String hash;
}
