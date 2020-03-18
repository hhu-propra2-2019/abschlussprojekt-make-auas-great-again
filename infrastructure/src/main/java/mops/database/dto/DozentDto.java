package mops.database.dto;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("dozent")
public class DozentDto {

  @Id
  Long id;
  String vorname;
  String nachname;
  String anrede;
  Set<FragebogenDto> frageboegen = new HashSet<>();
  Set<FragebogenDto> veranstaltungen = new HashSet<>();
}
