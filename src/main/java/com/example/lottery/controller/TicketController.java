package com.example.lottery.controller;

import com.example.lottery.service.TicketCreationService;
import com.example.lottery.service.TicketModificationService;
import com.example.lottery.service.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/ticket")
@Slf4j
public class TicketController {

  private final TicketService ticketService;
  private final TicketCreationService ticketCreationService;
  private final TicketModificationService ticketModificationService;

  @Autowired
  public TicketController(TicketService ticketService, TicketCreationService ticketCreationService,
      TicketModificationService ticketModificationService) {
    this.ticketService = ticketService;
    this.ticketCreationService = ticketCreationService;
    this.ticketModificationService = ticketModificationService;
  }

  @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity createTicket(@RequestBody int lines) {
    return ticketCreationService.createTicket(lines);
  }

  @GetMapping(value = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity getTicket(@PathVariable("id") int ticketId) {
    return ticketService.getTicket(ticketId);
  }

  @GetMapping(value = {""}, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity getTickets() {
    return ticketService.getTickets();
  }

  @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity updateTicket(@PathVariable("id") int ticketId, @RequestBody int lines) {
    return ticketModificationService.updateTicketLines(ticketId, lines);
  }

}
