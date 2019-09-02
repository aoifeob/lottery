package com.example.lottery.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.lottery.exception.TicketNotFoundException;
import com.example.lottery.model.Line;
import com.example.lottery.model.LineNumber;
import com.example.lottery.model.Ticket;
import com.example.lottery.repository.TicketRepository;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TicketStatusControllerTest {

  @Autowired
  private TicketRepository ticketRepository;

  @Autowired
  private MockMvc mockMvc;

  @Before
  public void setUp() {
    Ticket ticket = Ticket.builder().id(1).lines(Arrays
        .asList(new Line(Arrays.asList(new LineNumber(0), new LineNumber(1), new LineNumber(1))),
            new Line(Arrays.asList(new LineNumber(1), new LineNumber(1), new LineNumber(1)))))
        .build();
    ticketRepository.save(ticket);
  }

  @Test
  @Transactional
  public void getStatusForTicket() throws Exception {

    // Assert ticket status checked set to false and result values are null before checking status
    Ticket ticket = ticketRepository.findById(1)
        .orElseThrow(() -> new TicketNotFoundException("Ticket not found"));
    Assert.assertFalse(ticket.isStatusChecked());
    Assert.assertNull(ticket.getLines().get(0).getResult());
    Assert.assertNull(ticket.getLines().get(1).getResult());

    mockMvc.perform(put("/v1/status/1"))
        .andDo(print())
        .andExpect(status().isOk());

    // Assert ticket status checked set to true and result values are set after checking status
    ticket = ticketRepository.findById(1)
        .orElseThrow(() -> new TicketNotFoundException("Ticket not found"));
    Assert.assertTrue(ticket.isStatusChecked());
    Assert.assertEquals(5, (int) ticket.getLines().get(0).getResult());
    Assert.assertEquals(10, (int) ticket.getLines().get(1).getResult());

  }

  @Test
  public void getStatusForTicketNotCreated() throws Exception {

    mockMvc.perform(put("/v1/status/100"))
        .andDo(print())
        .andExpect(status().isNotFound());

  }

}
