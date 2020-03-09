package mops.database.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("studentBelegtEinheit")
public class StudentBelegtEinheitDto {
  Long einheit;
}
