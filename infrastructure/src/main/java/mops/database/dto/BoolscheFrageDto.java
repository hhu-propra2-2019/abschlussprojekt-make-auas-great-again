package mops.database.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("boolscheFrage")
public class BoolscheFrageDto {
  @Id
  Long id;
  
  @Column("frage")
  Set<BoolscheAntwortDto> antworten;
}
