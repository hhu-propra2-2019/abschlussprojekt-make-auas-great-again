package mops.database.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@Table("studentBelegtVeranstaltung")
public class SBelegtVDto {
  @Getter
  transient Long student;
}
