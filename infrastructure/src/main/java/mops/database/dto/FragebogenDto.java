package mops.database.dto;

import java.time.LocalDateTime;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mops.Einheit;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("fragebogen")
public class FragebogenDto {
  @Id
  private Long id;
  private String name;
  private String startzeit;
  private String endzeit;
  private Einheit einheit;
  
  @Column("fragebogen")
  private Set<FrageDto> fragen;
  
  @Column("fragebogen")
  private Set<StudentBeantwortetFragebogenDto> bearbeitet;
}
