package mops.database.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;

@Data
@AllArgsConstructor
@Table("dozent")
public class DozentDto {
  @Id
  Long id;
  String name;
}
