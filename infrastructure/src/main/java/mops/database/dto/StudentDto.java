package mops.database.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@Data
@Table("student")
public class StudentDto {

  @Id
  Long id;
  String username;
  String vorname;
  String nachname;


  public static StudentDto create(String username) {
    return new StudentDto(null, username, null, null);
  }

  public void addVornameNachname(String vorname, String nachname) {
    this.vorname = vorname;
    this.nachname = nachname;
  }
}
