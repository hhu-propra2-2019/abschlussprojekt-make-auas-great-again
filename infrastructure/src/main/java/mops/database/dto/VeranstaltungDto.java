package mops.database.dto;

import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@AllArgsConstructor
@Data
@Table("veranstaltung")
public class VeranstaltungDto {

  @Id
  Long id;
  String name;
  String semester;
  @Column("veranstaltung")
  Set<FragebogenDto> frageboegen;
  @Column("veranstaltung")
  Set<SBelegtVDto> studenten;


  public static VeranstaltungDto create(String name, String semester) {
    return new VeranstaltungDto(null, name, semester, new HashSet<>(),
        new HashSet<>());
  }

  public void addStudent(StudentDto student) {
    studenten.add(new SBelegtVDto(student.getId()));
  }


  public void addFragebogen(FragebogenDto fragebogen) {
    frageboegen.add(fragebogen);
  }
}
