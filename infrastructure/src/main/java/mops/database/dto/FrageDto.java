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
@Table("frage")
public class FrageDto {
  @Id
  Long id;
  String fragetext;
  Boolean oeffentlich;
  @Column("frage")
  Set<AntwortDto> antworten;
  @Column("frage")
  Set<AuswahlDto> auswaehlbar;

  public static FrageDto createTextfrage(String text) {
    return new FrageDto(null, text, false, new HashSet<>(), null);
  }

  public static FrageDto createMultipleChoicefrage(String text) {
    return new FrageDto(null, text, false, new HashSet<>(), new HashSet<>());
  }

  public void addChoice(AuswahlDto choice) {
    auswaehlbar.add(choice);
  }

  public void addAnwort(AntwortDto antwort) {
    antworten.add(antwort);
  }
}

