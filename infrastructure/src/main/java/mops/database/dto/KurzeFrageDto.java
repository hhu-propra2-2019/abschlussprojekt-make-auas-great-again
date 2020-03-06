package mops.database.dto;

import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;

@Data
@Table("kurzeFrage")
public class KurzeFrageDto {
  @Id
  Long id;
  
  @Column("frage")
  Set<KurzeAntwortDto> antworten;
}
