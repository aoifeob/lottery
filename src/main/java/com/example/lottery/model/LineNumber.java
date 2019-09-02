package com.example.lottery.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "line_number")
public class LineNumber {

  public LineNumber(int number) {
    this.number = number;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
  @SequenceGenerator(name = "generator", sequenceName = "line_number_seq", allocationSize = 1)
  @Column(name = "line_number_id")
  @JsonIgnore
  private int lineNumberId;

  private int number;

  @ManyToOne(targetEntity = Line.class)
  @JoinColumn(name = "line_id")
  @JsonIgnore
  private Line line;

}
