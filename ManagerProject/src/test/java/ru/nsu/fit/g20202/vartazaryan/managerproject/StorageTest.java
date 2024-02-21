package ru.nsu.fit.g20202.vartazaryan.managerproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.CrackDTO;
import ru.nsu.fit.g20202.vartazaryan.managerproject.storage.Status;
import ru.nsu.fit.g20202.vartazaryan.managerproject.storage.Ticket;
import ru.nsu.fit.g20202.vartazaryan.managerproject.storage.TicketStorage;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StorageTest
{
    /*@Autowired
    private TicketStorage ticketStorage;

    @BeforeEach
    public void setUp()
    {
        ticketStorage.deleteAllTickets();
    }

    @Test
    public void addTicketTest()
    {
        CrackDTO crackDTO = new CrackDTO();
        crackDTO.setHash("hash");
        crackDTO.setMaxLength(2);
        ticketStorage.addNewTicket(crackDTO);

        assertEquals(1, ticketStorage.getStorageSize());
    }

    @Test
    public void getTicketTest()
    {
        CrackDTO crackDTO = new CrackDTO();
        crackDTO.setHash("hash");
        crackDTO.setMaxLength(2);
        String id = ticketStorage.addNewTicket(crackDTO);

        Ticket savedTicket = ticketStorage.getTicket(id);

        assertEquals("hash", savedTicket.getHash());
        assertEquals(2, savedTicket.getMaxLength());
        assertEquals(Status.IN_PROGRESS, savedTicket.getStatus());
        assertTrue(savedTicket.getResult().isEmpty());
    }

    @Test
    public void updateTicketTest()
    {
        CrackDTO crackDTO = new CrackDTO();
        crackDTO.setHash("hash");
        crackDTO.setMaxLength(2);
        String id = ticketStorage.addNewTicket(crackDTO);

        List<String> data = new ArrayList<>();
        ticketStorage.updateTicket(id, data);
        var t = ticketStorage.getTicket(id);

        assertEquals(Status.IN_PROGRESS, t.getStatus());
        assertTrue(t.getResult().isEmpty());

        data = List.of("abc");
        ticketStorage.updateTicket(id, data);
        t = ticketStorage.getTicket(id);

        //assertEquals(Status.DONE, t.getStatus());
        //assertEquals("abc", t.getResult().get(0));
    }*/
}
