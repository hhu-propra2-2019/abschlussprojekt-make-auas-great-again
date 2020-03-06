package mops.database.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;

@Data
@Table("multipleAntwort")
public class MultipleAntwortDto {
  @Id
  Long id;
  int antwort;
}
