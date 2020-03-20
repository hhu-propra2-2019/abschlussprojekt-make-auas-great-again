package mops;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import mops.fragen.Frage;
import mops.rollen.Student;

@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class SubmitService {
  /**
   * Speichern der Antworten einer Umfrage in die Frage.
   *
   * @param fragebogen in den die Antworten gespeichert werden sollen
   * @param antworten Map mit zu speichernden Antworten
   */
  public void saveAntworten(Fragebogen fragebogen, Map<Long, String> antworten) {
    for (Frage frage : fragebogen.getFragen()) {
      String antwort = antworten.get(frage.getId());
      addAntwortIfValid(frage, antwort);
    }
  }

  private void addAntwortIfValid(Frage frage, String antwort) {
    if ((antwort != null) && !antwort.equals("")) {
      frage.addAntwort(antwort);
    }
  }

  public void addStudentAsSubmitted(Fragebogen fragebogen, Student student) {
    fragebogen.addStudentAsSubmitted(student);
  }

  public List<Fragebogen> notSubmittedFrageboegen(List<Fragebogen> frageboegen, Student student) {
    return frageboegen.stream()
        .filter(fragebogen -> !fragebogen.getAbgegebeneStudierende().contains(student))
        .collect(Collectors.toList());
  }

  public List<Fragebogen> frageboegenContaining(List<Fragebogen> frageboegen, String search) {
    return frageboegen.stream()
        .filter(fragebogen -> fragebogen.contains(search))
        .collect(Collectors.toList());
  }
}
