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
@Table("dozent")
public class DozentDto {
  @Id
  Long id;
  String name;
  
  @Column("dozent")
  Set<EinheitDto> einheiten;
  
  public DozentDto(String name, Set<EinheitDto> einheiten) {
    this.name = name;
    this.einheiten = einheiten;
  }
}
