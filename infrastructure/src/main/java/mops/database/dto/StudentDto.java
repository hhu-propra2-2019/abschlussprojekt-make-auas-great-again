package mops.database.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("student")
public class StudentDto {

  @Id
  String username;
  String vorname;
  String nachname;

  public StudentDto(String username) {
    this.username = username;
  }

  public StudentDto(String username, String vorname, String nachname) {
    this.username = username;
    this.vorname = vorname;
    this.nachname = nachname;
  }
}
