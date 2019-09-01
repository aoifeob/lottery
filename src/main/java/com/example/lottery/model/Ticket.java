package com.example.lottery.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ticket")
public class Ticket {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
  @SequenceGenerator(name = "generator", sequenceName = "ticket_seq", allocationSize = 1)
  @Column(name = "id")
  private int id;

  @Column(name = "statusChecked", nullable = false)
  private boolean isStatusChecked;

  @OneToMany(targetEntity = Line.class, cascade = CascadeType.ALL, mappedBy = "ticket")
  @OrderBy("result")
  private Set<Line> lines;

}
