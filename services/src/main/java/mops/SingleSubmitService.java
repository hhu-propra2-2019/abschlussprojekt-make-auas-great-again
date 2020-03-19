package mops;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import mops.rollen.Student;
@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class SingleSubmitService {

  public void addStudentAsSubmitted(Fragebogen fragebogen, Student student) {
    fragebogen.addStudentAsSubmitted(student);
  }

  public boolean hasSubmitted(Fragebogen fragebogen, Student student) {
    List<Student> abgegebeneStudierende = fragebogen.getAbgegebeneStudierende();
    for (Student studentin : abgegebeneStudierende) {
      if (studentin.equals(student)) {
        return true;
      }
    }
    return false;
  }

  public List<Fragebogen> notSubmittedFrageboegen(List<Fragebogen> frageboegen, Student student) {
    List<Fragebogen> notYetSubmitted = new ArrayList<>();
    for (Fragebogen bogen : frageboegen) {
      if (!hasSubmitted(bogen, student)) {
        notYetSubmitted.add(bogen);
      }
    }
    return notYetSubmitted;
  }

  public List<Fragebogen> frageboegenContaining(List<Fragebogen> frageboegen, String search) {
    return frageboegen.stream()
        .filter(fragebogen -> fragebogen.contains(search))
        .collect(Collectors.toList());
  }
}
