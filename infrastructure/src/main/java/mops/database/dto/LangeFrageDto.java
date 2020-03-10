package mops.database.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("langeFrage")
public class LangeFrageDto {
  @Id
  Long id;
  
  @Column("frage")
  Set<LangeAntwortDto> antworten;
}