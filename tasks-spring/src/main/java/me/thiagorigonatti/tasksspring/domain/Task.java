package me.thiagorigonatti.tasksspring.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;


@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "name", columnNames = {"name"})})
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonProperty("_id")
  private Long id;


  @Column(name = "name", nullable = false, unique = true)
  private String name;

  private BigDecimal cost;

  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate limitDate;

  public Task() {
  }

  public Task(Builder builder) {
    this.id = builder.id;
    this.name = builder.name;
    this.cost = builder.cost;
    this.limitDate = builder.limitDate;
    this.position = builder.position;
  }

  private Long position;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  public Long getPosition() {
    return position;
  }

  public void setPosition(Long position) {
    this.position = position;
  }


  public static class Builder {

    private Long id;
    private String name;
    private BigDecimal cost;
    private LocalDate limitDate;
    private Long position;

    public Builder(Long id, String name) {
      this.id = id;
      this.name = name;
    }

    public Builder(String name) {
      this.name = name;
    }

    public Builder() {

    }

    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder cost(BigDecimal cost) {
      this.cost = cost;
      return this;
    }

    public Builder limitDate(LocalDate limitDate) {
      this.limitDate = limitDate;
      return this;
    }

    public Builder position(Long position) {
      this.position = position;
      return this;
    }

    public Task build() {
      return new Task(this);
    }

  }


  @Override
  public String toString() {
    return "Task{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", cost=" + cost +
      ", limitDate=" + limitDate +
      ", position=" + position +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Task task)) return false;
    return Objects.equals(getId(), task.getId()) && Objects.equals(getName(), task.getName())
      && Objects.equals(getCost(), task.getCost()) && Objects.equals(getLimitDate(), task.getLimitDate())
      && Objects.equals(getPosition(), task.getPosition());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName(), getCost(), getLimitDate(), getPosition());
  }
}
