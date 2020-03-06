package mops.database.dto;

import lombok.AllArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;

@Data
@AllArgsConstructor
@Table("studentBeantwortetFragebogen")
public class StudentBeantwortetFragebogenDto {
  Long fragebogen;
}
