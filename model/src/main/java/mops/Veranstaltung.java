package mops;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import mops.rollen.Dozent;
import mops.rollen.Student;

@Builder
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "veranstaltungsNr")
public class Veranstaltung {
  private Long veranstaltungsNr;
  private String name;
  private String semester;
  private Dozent dozent;
  private List<Student> studenten;
  private List<Fragebogen> frageboegen;
  
  public Veranstaltung(String name, String semester, Dozent dozent) {
    Random idgenerator = new Random();
    this.veranstaltungsNr = idgenerator.nextLong();
    this.name = name;
    this.semester = semester;
    this.dozent = dozent;
    this.studenten = new ArrayList<>();
    this.frageboegen = new ArrayList<>();
  }

  public boolean contains(String search) {
    if (dozent.getNachname().toLowerCase(Locale.GERMAN)
        .contains(search.toLowerCase(Locale.GERMAN))) {
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
  
  public Fragebogen getFragebogenById(Long id) {
    Optional<Fragebogen> bogen = frageboegen.stream().filter(x -> x.getBogennr().equals(id))
        .findFirst();
    return bogen.get();
  }
}
