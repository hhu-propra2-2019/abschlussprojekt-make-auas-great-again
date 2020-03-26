package mops.database.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@Table("studentBeantwortetFragebogen")
public class SBeantwortetFDto {
  @Getter
  transient Long student;

}
