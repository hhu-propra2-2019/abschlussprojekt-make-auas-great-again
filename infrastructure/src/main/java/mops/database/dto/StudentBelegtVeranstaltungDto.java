package mops.database.dto;

import org.springframework.data.relational.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("studentBelegtVeranstaltung")
public class StudentBelegtVeranstaltungDto {
  private String student;
}
