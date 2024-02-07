package ru.nsu.fit.g20202.vartazaryan.managerproject.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskDTO
{
    private String ticketID;
    private int start;
    private int finish;
    private int maxLen;
}
