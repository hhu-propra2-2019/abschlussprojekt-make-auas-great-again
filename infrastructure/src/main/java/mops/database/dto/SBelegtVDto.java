package mops.database.dto;

import lombok.AllArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@Table("studentBelegtVeranstaltung")
public class SBelegtVDto {
  transient Long student;
}
