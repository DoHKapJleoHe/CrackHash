package ru.nsu.fit.g20202.vartazaryan.managerproject.storage;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Data
@Document("tickets")
public class Ticket
{
    private UUID ticketId;
    private Status status;
    private String hash;
    private int maxLength;

    private int tasksNumber;
    @Setter
    @Getter
    private int tasksDone;

    private LocalDateTime creationTime;
    private List<String> result;

    public Ticket(UUID ticketId, String hash, int maxLength)
    {
        this.ticketId = ticketId;
        this.status = Status.IN_PROGRESS;
        this.hash = hash;
        this.maxLength = maxLength;

        this.creationTime = LocalDateTime.now();
        this.result = new ArrayList<>();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
        {
            return true;
        }
        if (obj == null || getClass() != obj.getClass())
        {
            return false;
        }

        Ticket ticket = (Ticket) obj;

        return maxLength == ticket.maxLength && Objects.equals(ticketId, ticket.ticketId) && Objects.equals(hash, ticket.hash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId, hash, maxLength);
    }
}
