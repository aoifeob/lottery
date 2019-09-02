package com.example.lottery.service;

import com.example.lottery.exception.TicketNotFoundException;
import com.example.lottery.model.Ticket;
import com.example.lottery.repository.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TicketStatusService {

  private final TicketModificationService ticketModificationService;
  private final TicketRepository ticketRepository;

  @Autowired
  public TicketStatusService(TicketRepository ticketRepository,
      TicketModificationService ticketModificationService) {
    this.ticketRepository = ticketRepository;
    this.ticketModificationService = ticketModificationService;
  }

  public ResponseEntity getTicketStatus(int ticketId) {
    try {
      Ticket ticket = ticketRepository.findById(ticketId)
          .orElseThrow(() -> new TicketNotFoundException("Ticket " + ticketId + " not found"));
      if (!ticket.isStatusChecked()) {
        setTicketStatus(ticket);
      }
      return ResponseEntity.ok().body(ticket);
    } catch (TicketNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  private Ticket setTicketStatus(Ticket ticket) {
    return ticketModificationService.setLineResults(ticket);
  }

}
