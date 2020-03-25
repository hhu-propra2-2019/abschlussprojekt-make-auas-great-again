package mops.database.dto;

import lombok.AllArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@Table("studentBeantwortetFragebogen")
public class SBeantwortetFDto {
  transient Long student;

}
