package mops.database.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;

@Data
@Table("aufgabe")
public class AufgabeDto {
  @Id
  Long id;
  String name;
}
