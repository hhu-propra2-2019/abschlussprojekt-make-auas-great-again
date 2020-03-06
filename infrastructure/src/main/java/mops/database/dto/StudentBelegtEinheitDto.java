package mops.database.dto;

import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;

@Data
@Table("studentBelegtEinheit")
public class StudentBelegtEinheitDto {
  Long einheit;
}
