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
  Set<FragebogenDto> frageboegen = new HashSet<>();
  Set<sBelegtV> studenten = new HashSet<>();

  void addStudent(StudentDto student) {
    studenten.add(new sBelegtV(student.getUsername()));
  }
}
