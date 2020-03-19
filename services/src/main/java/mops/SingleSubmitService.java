package mops;

import mops.rollen.Student;

public class SingleSubmitService {

  public void addStudentAsSubmitted(Fragebogen fragebogen, Student student) {
    fragebogen.addStudentAsSubmitted(student);
  }
}
