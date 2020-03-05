package mops.database.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("Antworten")
public class AntwortenDto {
  @Id
  Long id;
  Long frageId;
  String antworttext;
}
