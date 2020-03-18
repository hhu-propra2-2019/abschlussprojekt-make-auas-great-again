package mops.database.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("frage")
public class FrageDto {
  @Id
  Long id;
  String titel;
}