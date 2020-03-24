package mops.database.dto;

import org.springframework.data.relational.core.mapping.Table;


@Table("studentBelegtVeranstaltung")
public class SBelegtVDto {
  transient Long student;

  public SBelegtVDto(Long student) {
    this.student = student;
  }

}
