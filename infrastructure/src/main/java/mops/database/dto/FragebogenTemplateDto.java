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
@Table("fragebogentemplate")
public class FragebogenTemplateDto {
  @Id
  private Long id;
  private String name;
  
  @Column("fragebogentemplate")
  private Set<FrageDto> fragen;
}
