package mops.database.dto;

import org.springframework.data.relational.core.mapping.Table;

@Table("dozentOrganisiertVeranstaltung")
public class dOrganisiertV {
  private Long dozent;

  public dOrganisiertV(Long dozent) {
    this.dozent = dozent;
  }
}
