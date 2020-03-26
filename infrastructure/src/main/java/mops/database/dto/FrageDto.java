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
  @Column("isMultipleResponse")
  Boolean isMultipleResponse;
  @Column("frage")
  Set<AntwortDto> antworten;
  @Column("frage")
  Set<AuswahlDto> auswaehlbar;

  public static FrageDto createTextfrage(String text) {
    return new FrageDto(null, text, false, false, new HashSet<>(), null);
  }

  public static FrageDto createMultipleResponsefrage(String text) {
    return new FrageDto(null, text, false, true, new HashSet<>(), new HashSet<>());
  }

  public static FrageDto createSingleResponsefrage(String text) {
    return new FrageDto(null, text, false, false, new HashSet<>(), new HashSet<>());
  }

  public void addChoice(AuswahlDto choice) {
    auswaehlbar.add(choice);
  }

  public void addAnwort(AntwortDto antwort) {
    antworten.add(antwort);
  }

  public boolean isMultipleResponseFrage() {
    return isMultipleResponse;
  }

  public boolean isSingleResponseFrage() {
    return !isMultipleResponse;
  }
}

