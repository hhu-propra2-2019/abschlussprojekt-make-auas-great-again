package mops.database.dto;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("student")
public class StudentDto {

  @Id
  Long id;
  String vorname;
  String nachname;
  Set<sBeantwortetF> beantworteteBoegen = new HashSet<>();

  void addBeantwortet(FragebogenDto fragebogen) {
    beantworteteBoegen.add(new sBeantwortetF(fragebogen.getId()));
  }
}
