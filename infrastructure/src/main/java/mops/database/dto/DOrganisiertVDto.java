package mops.database.dto;

import lombok.AllArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@Table("dozentOrganisiertVeranstaltung")
public class DOrganisiertVDto {
  transient Long dozent;
}
