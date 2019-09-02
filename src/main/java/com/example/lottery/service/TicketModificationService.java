package com.example.lottery.service;

import com.example.lottery.exception.TicketNotEditableException;
import com.example.lottery.exception.TicketNotFoundException;
import com.example.lottery.model.Line;
import com.example.lottery.model.Ticket;
import com.example.lottery.repository.TicketRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class TicketModificationService {

  private final TicketRepository ticketRepository;
  private final ResultCalculationService resultCalculationService;
  private final LineGenerationService lineGenerationService;

  @Autowired
  public TicketModificationService(TicketRepository ticketRepository,
      ResultCalculationService resultCalculationService,
      LineGenerationService lineGenerationService) {
    this.ticketRepository = ticketRepository;
    this.resultCalculationService = resultCalculationService;
    this.lineGenerationService = lineGenerationService;
  }

  public ResponseEntity updateTicketLines(int ticketId, int numLines) {
    if (!isValidNumberOfLines(numLines)) {
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
          "Invalid number of lines provided for ticket");
    }
    Ticket ticket = ticketRepository.findById(ticketId)
        .orElseThrow(() -> new TicketNotFoundException("Ticket " + ticketId + " not found"));
    try {
      addLinesToTicket(ticket, numLines);
    } catch (TicketNotEditableException e) {
      log.error("Could not add lines to ticket {}", ticketId, e);
      return ResponseEntity.unprocessableEntity().build();
    }
    return ResponseEntity.ok().body(ticket);
  }

  private boolean isValidNumberOfLines(int numLines) {
    return numLines > 0;
  }

  private Ticket addLinesToTicket(Ticket ticket, int numLines) {
    if (canAddLines(ticket)) {
      ticket = saveLinesToTicket(ticket, lineGenerationService.generateLines(numLines));
    } else {
      throw new TicketNotEditableException(
          "Cannot add lines to a ticket which has already been checked");
    }
    return ticket;
  }

  private Ticket saveLinesToTicket(Ticket ticket, List<Line> additionalLines) {
    for (Line line : additionalLines) {
      ticket.getLines().add(line);
    }
    return ticketRepository.save(ticket);
  }

  Ticket setLineResults(Ticket ticket) {
    for (Line line : ticket.getLines()) {
      int result = resultCalculationService.calculateResultForLine(line.getLineNumbers());
      line.setResult(result);
    }
    ticket.setStatusChecked(true);
    return ticketRepository.saveAndFlush(ticket);
  }

  private boolean canAddLines(Ticket ticket) {
    return !ticket.isStatusChecked();
  }

}
