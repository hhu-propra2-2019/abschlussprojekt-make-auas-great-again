package mops.database.dto;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;

@Data
@AllArgsConstructor
@Table("langeAntwort")
public class LangeAntwortDto {
  @Id
  Long id;
  String antwort;
}
