package com.example.lottery.repository;

import com.example.lottery.model.Line;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineRepository extends JpaRepository<Line, Integer> {

  @Override
  <S extends Line> S save(S s);

}
