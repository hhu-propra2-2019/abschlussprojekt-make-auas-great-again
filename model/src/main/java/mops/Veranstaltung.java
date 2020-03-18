package mops;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import mops.rollen.Dozent;
import mops.rollen.Student;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "veranstaltungsNr")
public class Veranstaltung {
  private Long veranstaltungsNr;
  private String name;
  private String semester;
  private Dozent dozent;
  private List<Student> studenten;
  private List<Fragebogen> frageboegen;

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

  public void addFragebogen(Fragebogen fragebogen) {
    frageboegen.add(fragebogen);
  }

  public List<Fragebogen> getFrageboegenContaining(String search) {
    return frageboegen.stream()
        .filter(fragebogen -> fragebogen.contains(search))
        .collect(Collectors.toList());
  }

  public boolean hasStudent(Student student) {
    return studenten.contains(student);
  }
}
