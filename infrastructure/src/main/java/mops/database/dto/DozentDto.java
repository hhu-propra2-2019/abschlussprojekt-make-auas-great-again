package mops.database.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@Data
@Table("dozent")
public class DozentDto {

  @Id
  Long id;
  String vorname;
  String nachname;
  String anrede;

  public static DozentDto create(String vorname, String nachname, String anrede) {
    return new DozentDto(null, vorname, nachname, anrede);
  }

}
