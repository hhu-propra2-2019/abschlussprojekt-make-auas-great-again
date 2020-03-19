package mops.database.dto;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Data
@Table("veranstaltung")
public class VeranstaltungDto {

  @Id
  Long id;
  String name;
  Integer semester;
  Set<FragebogenDto> frageboegen;
  Set<sBelegtV> studenten;
  Set<dOrganisiertV> dozenten;

  public VeranstaltungDto(Long id, String name, Integer semester) {
    this.id = id;
    this.name = name;
    this.semester = semester;
    this.frageboegen = new HashSet<>();
    this.studenten = new HashSet<>();
    this.dozenten = new HashSet<>();
  }

  public VeranstaltungDto(Long id, String name, Integer semester, Set<FragebogenDto> frageboegen, Set<sBelegtV> studenten, Set<dOrganisiertV> dozenten) {
    this.id = id;
    this.name = name;
    this.semester = semester;
    this.frageboegen = frageboegen;
    this.studenten = studenten;
    this.dozenten = dozenten;
  }

  public void addStudent(StudentDto student) {
    studenten.add(new sBelegtV(student.getUsername()));
  }

  public void addDozent(DozentDto dozent) {
    dozenten.add(new dOrganisiertV(dozent.getId()));
  }

  public void addFragebogen(FragebogenDto fragebogen) {
    frageboegen.add(fragebogen);
  }
}
