package mops.database.dto;

import org.springframework.data.relational.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("dozentOrganisiertVeranstaltung")
public class DozentOrganisiertVeranstaltungDto {
  private String dozent;
}
