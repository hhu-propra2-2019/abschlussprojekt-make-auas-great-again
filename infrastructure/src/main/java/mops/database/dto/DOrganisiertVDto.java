package mops.database.dto;

import org.springframework.data.relational.core.mapping.Table;

@Table("dozentOrganisiertVeranstaltung")
public class DOrganisiertVDto {
  transient Long dozent;

  public DOrganisiertVDto(Long dozent) {
    this.dozent = dozent;
  }
}
