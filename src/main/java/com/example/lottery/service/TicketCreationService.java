package com.example.lottery.service;

import com.example.lottery.model.Ticket;
import com.example.lottery.repository.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TicketCreationService {

  private final LineGenerationService lineGenerationService;
  private final TicketRepository ticketRepository;

  @Autowired
  public TicketCreationService(LineGenerationService lineGenerationService,
      TicketRepository ticketRepository) {
    this.lineGenerationService = lineGenerationService;
    this.ticketRepository = ticketRepository;
  }

  public ResponseEntity createTicket(int numLines) {
    if (numLines > 0) {
      try {
        Ticket ticket = createAndSaveTicket(numLines);
        log.debug("Created ticket with id ");
        return ResponseEntity.ok().body(ticket);
      } catch (Exception e) {
        log.error("Could not create ticket. Error: ", e);
      }
    }
    return ResponseEntity.unprocessableEntity().build();
  }

  private Ticket createAndSaveTicket(int numLines) {
    Ticket ticket = Ticket.builder()
        .lines(lineGenerationService.generateLines(numLines)).build();
    return ticketRepository.save(ticket);
  }

}
