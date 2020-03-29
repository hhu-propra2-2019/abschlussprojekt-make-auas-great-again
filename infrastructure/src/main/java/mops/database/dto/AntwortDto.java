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
@Table("antwort")
public class AntwortDto {
  @Id
  private Long id;
  private String textantwort;
  private Long antworttyp;
  
  @Column("antwort")
  private Set<AuswahlDto> auswahlen;
  
  public boolean isTextAntwort() {
    return antworttyp == 1L;
  }
}
