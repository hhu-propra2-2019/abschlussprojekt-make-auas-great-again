package mops.database.dto;

import java.util.HashSet;
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
  private String username;
  private String vorname;
  private String nachname;
  
  @Column("dozent")
  private Set<FragebogenTemplateDto> templates;
  
  public DozentDto(String username) {
    this.username = username;
    this.templates = new HashSet<>();
  }
}
