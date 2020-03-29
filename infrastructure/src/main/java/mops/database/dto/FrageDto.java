package mops.database.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("frage")
public class FrageDto {
  @Id
  private Long id;
  private Boolean oeffentlich;
  private Long fragetyp;
  private String fragetext;
  
  @Column("frage")
  private Set<AntwortDto> antworten;
  
  @Column("frage")
  private Set<AuswahlDto> auswahlen;
  
  public boolean isTextFrage() {
    return fragetyp == 1L;
  }
  
  public boolean isMultipleChoice() {
    return fragetyp == 2L;
  }
  
  public boolean isMultipleResponse() {
    return fragetyp == 3L;
  }
  
  public FrageDto(Boolean oeffentlich, Long fragetyp, String fragetext,
      Set<AntwortDto> antworten, Set<AuswahlDto> auswahlen) {
    this.oeffentlich = oeffentlich;
    this.fragetyp = fragetyp;
    this.fragetext = fragetext;
    this.antworten = antworten;
    this.auswahlen = auswahlen;
  }
}
