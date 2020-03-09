package mops.database.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("student")
public class StudentDto {
  @Id
  Long id;
  
  Set<StudentBeantwortetFragebogenDto> frageboegen;
  
  Set<StudentBelegtEinheitDto> einheiten;
}
