package mops.database.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@Data
@Table("auswahl")
public class AuswahlDto {
  @Id
  Long id;
  String auswahltext;

  public static AuswahlDto create(String text) {
    return new AuswahlDto(null, text);
  }
}