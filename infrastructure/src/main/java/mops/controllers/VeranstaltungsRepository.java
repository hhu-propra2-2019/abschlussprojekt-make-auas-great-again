package mops.controllers;

import java.util.List;
import mops.Fragebogen;
import mops.Veranstaltung;
import mops.rollen.Dozent;
import mops.rollen.Student;

public interface VeranstaltungsRepository {

  List<Veranstaltung> getAll();

  List<Veranstaltung> getAllContaining(String search);

  Veranstaltung getVeranstaltungById(Long id);

  void save(Veranstaltung veranstaltung);

  void addStudentToVeranstaltungById(Student student, Long verId);

  List<Veranstaltung> getAllFromStudent(Student student);

  List<Veranstaltung> getAllFromStudentContaining(Student student, String search);

  List<Veranstaltung> getAllFromDozent(Dozent dozent);

  List<Veranstaltung> getAllFromDozentContaining(Dozent dozent, String suche);


  Fragebogen getFragebogenById(Long fragebogen);

  Dozent getDozentByUsername(String name);

  void saveToVeranstaltung(Fragebogen fragebogen, Long veranstaltungid);
}
