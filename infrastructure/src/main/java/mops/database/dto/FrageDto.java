package mops.database.dto;

import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("frage")
public class FrageDto {
  @Id
  private Long id;
  private Boolean oeffentlich;
  private Boolean ismultipleresponse;
  private String fragetext;
  
  @Column("frage")
  private Set<AntwortDto> antworten;
  
  @Column("frage")
  private Set<AuswahlDto> auswahlen;
}
