package mops.database.dto;

import org.springframework.data.relational.core.mapping.Table;


@Table("studentBelegtVeranstaltung")
public class sBelegtV {
  transient Long student;

  public sBelegtV(Long student) {
    this.student = student;
  }

}
