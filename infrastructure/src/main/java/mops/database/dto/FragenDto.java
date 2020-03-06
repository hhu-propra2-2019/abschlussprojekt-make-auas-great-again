package mops.database.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("Fragen")
public class FragenDto {

  @Id
  Long id;
  String antwortmoeglichkeiten;
  String fragentext;
  String typ;
  Long formularId;
}
