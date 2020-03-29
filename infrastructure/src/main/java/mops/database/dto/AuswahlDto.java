package mops.database.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("auswahl")
public class AuswahlDto {
  @Id
  private Long id;
  private String auswahltext;
  
  public AuswahlDto(String auswahltext) {
    this.auswahltext = auswahltext;
  }
}
