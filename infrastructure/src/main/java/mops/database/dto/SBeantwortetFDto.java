package mops.database.dto;

import org.springframework.data.relational.core.mapping.Table;

@Table("studentBeantwortetFragebogen")
public class SBeantwortetFDto {
  transient Long student;

  public SBeantwortetFDto(Long student) {
    this.student = student;
  }
}
