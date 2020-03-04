package mops.feedback.formulare;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Table("Formular")
public class FormularDao {
  @Id
  Long id;
  @DateTimeFormat
  String erstelltAm;
  @DateTimeFormat
  String guerltigBis;
}
