package mops.controllers;

import java.util.List;
import mops.Veranstaltung;
import mops.rollen.Student;

public interface VeranstaltungsRepository {

  List<Veranstaltung> getAll();

  List<Veranstaltung> getAllContaining(String search);

  Veranstaltung getVeranstaltungById(Long id);

  void addStudentToVeranstaltungById(Student student, Long verId);

}
