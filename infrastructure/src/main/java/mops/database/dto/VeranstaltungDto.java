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
@Table("veranstaltung")
public class VeranstaltungDto {

  @Id
  Long id;
  String name;
  Integer semester;
  @Column("veranstaltung")
  Set<FragebogenDto> frageboegen;
  @Column("veranstaltung")
  Set<sBelegtV> studenten;
  @Column("veranstaltung")
  Set<dOrganisiertV> dozenten;


  public static VeranstaltungDto create(String name, Integer semester) {
    return new VeranstaltungDto(null, name, semester, new HashSet<>(), new HashSet<>(), new HashSet<>());
  }

  public void addStudent(StudentDto student) {
    studenten.add(new sBelegtV(student.getId()));
  }

  public void addDozent(DozentDto dozent) {
    dozenten.add(new dOrganisiertV(dozent.getId()));
  }

  public void addFragebogen(FragebogenDto fragebogen) {
    frageboegen.add(fragebogen);
  }
}
