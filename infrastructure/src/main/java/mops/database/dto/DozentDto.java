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
@Table("dozent")
public class DozentDto {

  @Id
  Long id;
  String username;
  String vorname;
  String nachname;
  String anrede;
  @Column("dozent")
  Set<VeranstaltungDto> veranstaltungen;
  Set<FragebogenTemplateDto> fragebogenTemplates;

  public static DozentDto create(String username, String vorname, String nachname) {
    return new DozentDto(null, username, vorname, nachname, null, new HashSet<>(), new HashSet<>());
  }

  public void addVeranstaltung(VeranstaltungDto veranstaltung) {
    veranstaltungen.add(veranstaltung);
  }

  public void addFragebogenTemplate(FragebogenTemplateDto fragebogenTemplate) {
    fragebogenTemplates.add(fragebogenTemplate);
  }
}
