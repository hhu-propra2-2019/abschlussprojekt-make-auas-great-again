package mops;

import java.util.ArrayList;
import java.util.List;
import mops.rollen.Student;

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
}
