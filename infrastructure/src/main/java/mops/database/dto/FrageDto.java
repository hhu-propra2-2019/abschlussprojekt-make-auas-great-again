package mops.database.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;

@Data
@Table("frage")
public class FrageDto {
  @Id
  Long id;
  String titel;
}
