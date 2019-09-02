package com.example.lottery.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.lottery.model.Line;
import com.example.lottery.model.LineNumber;
import com.example.lottery.model.Ticket;
import com.example.lottery.repository.TicketRepository;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class TicketControllerTest {

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
    ticket = Ticket.builder().id(2).lines(Arrays
        .asList(new Line(Arrays.asList(new LineNumber(2), new LineNumber(2), new LineNumber(1))),
            new Line(Arrays.asList(new LineNumber(2), new LineNumber(2), new LineNumber(2)))))
        .build();
    ticketRepository.save(ticket);
    ticket = Ticket.builder().id(3).isStatusChecked(true).lines(Arrays
        .asList(new Line(Arrays.asList(new LineNumber(2), new LineNumber(2), new LineNumber(1))),
            new Line(Arrays.asList(new LineNumber(2), new LineNumber(2), new LineNumber(2)))))
        .build();
    ticketRepository.save(ticket);
  }

  @Test
  public void testCreateTicket() throws Exception {

    mockMvc.perform(post("/v1/ticket").contentType(MediaType.APPLICATION_JSON).content("3"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("id", is(4)))
        .andExpect(jsonPath("lines", hasSize(3)));

    // delete ticket to prevent interference with other tests
    ticketRepository.deleteById(4);

  }

  @Test
  public void testGetAllTickets() throws Exception {

    mockMvc.perform(get("/v1/ticket"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(3)));

  }

  @Test
  public void testGetTicketById() throws Exception {

    mockMvc.perform(get("/v1/ticket/1"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("id", is(1)));

  }

  @Test
  public void testGetTicketByIdTicketNotCreated() throws Exception {

    mockMvc.perform(get("/v1/ticket/100"))
        .andDo(print())
        .andExpect(status().isNotFound());

  }

  @Test
  public void testAddLinesToTicket() throws Exception {

    mockMvc.perform(put("/v1/ticket/2").contentType(MediaType.APPLICATION_JSON).content("2"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("lines", hasSize(4)));

  }

  @Test
  public void testAddLinesToTicketForTicketNotCreated() throws Exception {

    mockMvc.perform(put("/v1/ticket/100").contentType(MediaType.APPLICATION_JSON).content("2"))
        .andDo(print())
        .andExpect(status().isNotFound());

  }

  @Test
  public void testAddLinesToTicketAlreadyChecked() throws Exception {

    mockMvc.perform(put("/v1/ticket/3").contentType(MediaType.APPLICATION_JSON).content("2"))
        .andDo(print())
        .andExpect(status().isUnprocessableEntity());

  }

}
