package mops.feedback.formulare;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("Fragen")
public class FragenDao {

  @Id
  Long id;
  String antwortmoeglichkeiten;
  String fragentext;
  String typ;
  Long formularId;
}
