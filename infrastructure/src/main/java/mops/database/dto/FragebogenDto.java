package mops.database.dto;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Table("fragebogen")
public class FragebogenDto {
  @Id
  Long id;
  String name;
  Integer status;
  Enum einheit;
  @DateTimeFormat
  String startzeit;
  @DateTimeFormat
  String endzeit;
  Set<FrageDto> fragen = new HashSet<>();
  Set<sBeantwortetF> beantworteteBoegen = new HashSet<>();

  void addBeantwortetVonStudent(StudentDto student) {
    beantworteteBoegen.add(new sBeantwortetF(student.getUsername()));
  }
}
