package me.thiagorigonatti.tasksspring.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TaskDtoPatchBody {

  @Length(min = 2, max = 128)
  private String name;

  @Range(min = 0, max = Integer.MAX_VALUE, message = "The cost must be between 0,00 and 2.147.483.647,00")
  private BigDecimal cost;


  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate limitDate;


  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public BigDecimal getCost() {
    return cost;
  }


  public void setCost(BigDecimal cost) {
    this.cost = cost;
  }


  public LocalDate getLimitDate() {
    return limitDate;
  }


  public void setLimitDate(LocalDate limitDate) {
    this.limitDate = limitDate;
  }
}
