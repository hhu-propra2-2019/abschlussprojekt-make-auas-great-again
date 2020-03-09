package mops.database.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("Fragebogen")
public class FragebogenDto {
  @Id
  Long id;
  String name;
  String beschreibung;
  @DateTimeFormat
  String startzeit;
  @DateTimeFormat
  String endzeit;
  
  @Column("fragebogen")
  Set<FrageDto> fragen;
}
