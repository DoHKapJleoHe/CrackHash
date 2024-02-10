package ru.nsu.fit.g20202.vartazaryan.workerproject.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResponseDTO
{
    private String ticketID;
    private List<String> result;
}
