package com.example.lottery.service;

import com.example.lottery.exception.TicketNotFoundException;
import com.example.lottery.model.Ticket;
import com.example.lottery.repository.TicketRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TicketService {

  private final TicketRepository ticketRepository;

  @Autowired
  public TicketService(TicketRepository ticketRepository) {
    this.ticketRepository = ticketRepository;
  }

  public ResponseEntity getTickets() {
    List<Ticket> tickets = ticketRepository.findAll();
    return ResponseEntity.ok().body(tickets);
  }

  public ResponseEntity getTicket(int ticketId) {
    try {
      Ticket ticket = ticketRepository.findById(ticketId)
          .orElseThrow(() -> new TicketNotFoundException("Ticket " + ticketId + " not found"));
      return ResponseEntity.ok().body(ticket);
    } catch (TicketNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

}
