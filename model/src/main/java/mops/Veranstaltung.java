package mops;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
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
@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class Veranstaltung {
  private Long veranstaltungsNr;
  private String name;
  private String semester;
  private List<Dozent> dozenten;
  private List<Student> studenten;
  private List<Fragebogen> frageboegen;

  public Veranstaltung(String name, String semester, Dozent dozent) {
    Random idgenerator = new Random();
    this.veranstaltungsNr = idgenerator.nextLong();
    this.name = name;
    this.semester = semester;
    this.dozenten = new LinkedList<>();
    dozenten.add(dozent);
    this.studenten = new ArrayList<>();
    this.frageboegen = new ArrayList<>();
  }

  public boolean contains(String search) {
    for (Dozent dozent : dozenten) {
      if (dozent.getNachname().toLowerCase(Locale.GERMAN)
          .contains(search.toLowerCase(Locale.GERMAN))) {
        return true;
      }
    }
    return name.toLowerCase(Locale.GERMAN)
        .contains(search.toLowerCase(Locale.GERMAN));
  }

  public void addStudent(Student student) {
    studenten.add(student);
  }
  
  public String getDozentenNamen() {
    String result = "";
    for (Dozent dozent : dozenten) {
      result += dozent.toString() + ", ";
    }
    result = result.substring(0, result.length()-2);
    return result;
  }

  public void addFragebogen(Fragebogen fragebogen) {
    frageboegen.add(fragebogen);
  }
  
  public void addDozent(Dozent dozent) {
    dozenten.add(dozent);
    frageboegen.stream().forEach(x -> x.updateDozenten(this.getDozentenNamen()));
  }

  public List<Fragebogen> getFrageboegenContaining(String search) {
    return frageboegen.stream()
        .filter(fragebogen -> fragebogen.contains(search))
        .collect(Collectors.toList());
  }

  public boolean hasStudent(Student student) {
    return studenten.contains(student);
  }

  public boolean hasDozent(Dozent dozent) {
    return this.dozent.equals(dozent);
  }
}
