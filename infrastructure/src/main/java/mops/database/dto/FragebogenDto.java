package mops.database.dto;

import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import mops.Einheit;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;

@AllArgsConstructor
@Data
@Table("fragebogen")
public class FragebogenDto {
  @Id
  Long id;
  String name;
  Integer status;
  Einheit einheit;
  @DateTimeFormat
  String startzeit;
  @DateTimeFormat
  String endzeit;
  @Column("fragebogen")
  Set<FrageDto> fragen;
  @Column("fragebogen")
  Set<sBeantwortetF> bearbeitetVon;


  public static FragebogenDto create(String name, Einheit einheit, String startzeit, String endzeit) {
    return new FragebogenDto(null, name, 0, einheit, startzeit, endzeit, new HashSet<>(), new HashSet<>());
  }

  public void addFrage(FrageDto frage) {
    fragen.add(frage);
  }

  void addBeantwortetVonStudent(StudentDto student) {
    bearbeitetVon.add(new sBeantwortetF(student.getId()));
  }
}
