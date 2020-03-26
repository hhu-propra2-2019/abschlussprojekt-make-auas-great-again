package mops.database.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@Table("dozentOrganisiertVeranstaltung")
public class DOrganisiertVDto {
  @Getter
  transient Long dozent;

  @Override
  public String toString() {
    return dozent.toString();
  }
}