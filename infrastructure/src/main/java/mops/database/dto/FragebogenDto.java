package mops.database.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
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
  Enum einheit;
  @DateTimeFormat
  String startzeit;
  @DateTimeFormat
  String endzeit;
  Set<FrageDto> fragen;
  Set<sBeantwortetF> bearbeitetVon;


  public static FragebogenDto create(String name, Enum einheit, String startzeit, String endzeit) {
    return new FragebogenDto(null, name, 0, einheit, startzeit, endzeit, null, null);
  }

  public void addFrage(FrageDto frage) {
    fragen.add(frage);
  }

  void addBeantwortetVonStudent(StudentDto student) {
    bearbeitetVon.add(new sBeantwortetF(student.getId()));
  }
}
