package mops;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import mops.rollen.Dozent;
import mops.rollen.Student;

@Builder
@Getter
@Setter
public class Veranstaltung {
  private final Long id;
  private String name;
  private String semester;
  private Dozent dozent;
  private List<Student> studenten;
}
