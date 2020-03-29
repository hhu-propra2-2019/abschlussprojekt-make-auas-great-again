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
@Table("veranstaltung")
@SuppressWarnings("PMD")
public class VeranstaltungDto {
  @Id
  private Long id;
  private String name;
  private String semester;
  
  @Column("veranstaltung")
  private Set<FragebogenDto> frageboegen;
  
  @Column("veranstaltung")
  private Set<StudentBelegtVeranstaltungDto> belegt;
  
  @Column("veranstaltung")
  private Set<DozentOrganisiertVeranstaltungDto> organisiert;
  
  public VeranstaltungDto(String name, String semester, Set<FragebogenDto> frageboegen,
      Set<StudentBelegtVeranstaltungDto> belegt,
      Set<DozentOrganisiertVeranstaltungDto> organisiert) {
    this.name = name;
    this.semester = semester;
    this.frageboegen = frageboegen;
    this.belegt = belegt;
    this.organisiert = organisiert;
  }
}
