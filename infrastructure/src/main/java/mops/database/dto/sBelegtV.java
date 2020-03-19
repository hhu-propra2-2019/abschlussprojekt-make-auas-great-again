package mops.database.dto;

import org.springframework.data.relational.core.mapping.Table;

@Table("studentBelegtVeranstaltung")
public class sBelegtV {
  private String student;

  public sBelegtV(String student) {
    this.student = student;
  }
}
