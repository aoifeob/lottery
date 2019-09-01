package com.example.lottery.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "line")
public class Line {

  public Line(List<LineNumber> lineNumbers) {
    this.lineNumbers = lineNumbers;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
  @SequenceGenerator(name = "generator", sequenceName = "line_seq", allocationSize = 1)
  @Column(name = "line_id")
  @JsonIgnore
  private int lineId;

  @ManyToOne(targetEntity = Ticket.class)
  @JoinColumn(name = "id")
  @JsonIgnore
  private Ticket ticket;

  @OneToMany(cascade = CascadeType.ALL)
  @OrderBy("line_number_id")
  @JoinColumn(name = "id")
  private List<LineNumber> lineNumbers;

  @Column(name = "result")
  private Integer result;

}
