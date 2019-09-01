package com.example.lottery.repository;

import com.example.lottery.model.LineNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineNumberRepository extends JpaRepository<LineNumber, Integer> {

  @Override
  <S extends LineNumber> S save(S s);

}
