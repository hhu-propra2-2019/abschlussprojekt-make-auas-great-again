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
}
