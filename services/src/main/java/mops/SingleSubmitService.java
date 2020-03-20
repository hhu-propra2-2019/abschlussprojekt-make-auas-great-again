package mops;

import java.util.List;
import java.util.stream.Collectors;
import mops.rollen.Student;
@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class SingleSubmitService {

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
