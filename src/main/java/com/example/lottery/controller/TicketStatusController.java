package com.example.lottery.controller;

import com.example.lottery.service.TicketStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/status")
@Slf4j
public class TicketStatusController {

  private final TicketStatusService ticketStatusService;

  @Autowired
  public TicketStatusController(TicketStatusService ticketStatusService) {
    this.ticketStatusService = ticketStatusService;
  }

  @PutMapping(value = "/{id}", produces = "application/json")
  public ResponseEntity getTicketStatus(@PathVariable("id") int ticketId) {
    return ticketStatusService.getTicketStatus(ticketId);
  }

}
