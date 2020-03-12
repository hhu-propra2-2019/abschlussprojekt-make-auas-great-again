package mops;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mops.rollen.Student;

@Getter
@Setter
@NoArgsConstructor
public class Kontaktformular {

  private Long id;
  private Student student;
  private long dozent;
  private LocalDateTime zeitpunkt;
  private String betreff;
  private String text;

}
