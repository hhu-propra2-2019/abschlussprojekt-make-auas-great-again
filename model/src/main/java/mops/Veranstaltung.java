package mops;

import java.util.List;
import java.util.Locale;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import mops.rollen.Dozent;
import mops.rollen.Student;

@Builder
@Getter
@Setter
public class Veranstaltung {
  private String name;
  private String semester;
  private Dozent dozent;
  private List<Student> studenten;

  public boolean contains(String search) {
    if (dozent.getNachname().toLowerCase(Locale.GERMAN).contains(search.toLowerCase(Locale.GERMAN))) {
      return true;
    } else {
      return name.toLowerCase(Locale.GERMAN)
          .contains(search.toLowerCase(Locale.GERMAN));
    }
  }

  public void addStudent(Student student) {
    studenten.add(student);
  }
}
