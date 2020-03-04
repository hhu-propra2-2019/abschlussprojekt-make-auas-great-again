package mops.feedback.formulare;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("Fragen")
public class FragenDAO {

  @Id
  Long id;
  String Antwortmoeglichkeiten;
  String fragentext;
  String typ;
  Long formularId;
}
