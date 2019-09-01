package com.example.lottery.repository;

import com.example.lottery.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

  @Override
  <S extends Ticket> S save(S s);

}
