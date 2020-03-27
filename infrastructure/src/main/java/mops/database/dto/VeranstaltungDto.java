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
@Table("veranstaltung")
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
}
