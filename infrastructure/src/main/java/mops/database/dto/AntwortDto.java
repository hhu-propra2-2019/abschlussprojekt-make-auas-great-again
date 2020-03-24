package mops.database.dto;

import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@AllArgsConstructor
@Data
@Table("antwort")
public class AntwortDto {
  @Id
  Long id;
  String textantwort;
  @Column("antwort")
  Set<AuswahlDto> ausgewaehlt;

  public static AntwortDto createTextantwort(String text) {
    return new AntwortDto(null, text, null);
  }

  public static AntwortDto createMultipleChoiceAntwort() {
    return new AntwortDto(null, null, new HashSet<>());
  }

  public void addAntwortToMultipleChoice(AuswahlDto auswahl) {
    ausgewaehlt.add(auswahl);
  }

  public void setAntwortenToMultipleChoice(Set<AuswahlDto> auswahlen) {
    ausgewaehlt = auswahlen;
  }
}
