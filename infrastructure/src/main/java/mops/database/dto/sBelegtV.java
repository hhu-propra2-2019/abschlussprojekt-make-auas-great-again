package mops.database.dto;

import org.springframework.data.relational.core.mapping.Table;


@Table("studentBelegtVeranstaltung")
public class sBelegtV {
  private Long student;

  public sBelegtV(Long student) {
    this.student = student;
  }
}
