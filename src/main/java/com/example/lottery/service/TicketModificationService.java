package com.example.lottery.service;

import com.example.lottery.exception.TicketNotEditableException;
import com.example.lottery.exception.TicketNotFoundException;
import com.example.lottery.model.Line;
import com.example.lottery.model.Ticket;
import com.example.lottery.repository.TicketRepository;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
      log.debug("Invalid number of lines provided");
      return ResponseEntity.unprocessableEntity().build();
    }
    try {
      Ticket ticket = ticketRepository.findById(ticketId)
          .orElseThrow(() -> new TicketNotFoundException("Ticket " + ticketId + " not found"));
      addLinesToTicket(ticket, numLines);
      return ResponseEntity.ok().body(ticket);
    } catch (TicketNotFoundException e) {
      return ResponseEntity.notFound().build();
    } catch (TicketNotEditableException e) {
      log.debug("Ticket {} cannot be edited", ticketId);
      return ResponseEntity.unprocessableEntity().build();
    }
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
    ticket.setLines(ticket.getLines().stream().sorted(Comparator.comparing(Line::getResult))
        .collect(Collectors.toList()));
    return ticketRepository.save(ticket);
  }

  private boolean canAddLines(Ticket ticket) {
    return !ticket.isStatusChecked();
  }

}
