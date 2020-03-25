package mops.database.dto;

import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@AllArgsConstructor
@Data
@Table("fragebogentemplate")
public class FragebogenTemplateDto {
  @Id
  Long id;
  String name;
  @Column("fragebogentemplate")
  Set<FrageDto> fragen;

  public static FragebogenTemplateDto create(String name) {
    return new FragebogenTemplateDto(null, name, new HashSet<>());
  }

  public void addFrage(FrageDto frage) {
    fragen.add(frage);
  }

  public void addFragen(Set<FrageDto> fragen) {
    fragen.addAll(fragen);
  }
}
