package ru.nsu.fit.g20202.vartazaryan.managerproject.storage;

import lombok.Data;

import java.util.Objects;
import java.util.UUID;

@Data
public class Ticket
{
    private UUID ticketId;
    private String hash;
    private int maxLength;

    public Ticket(UUID ticketId, String hash, int maxLength)
    {
        this.ticketId = ticketId;
        this.hash = hash;
        this.maxLength = maxLength;
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
