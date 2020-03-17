package mops.controllers;

import java.util.List;
import java.util.UUID;
import mops.Fragebogen;

public interface FragebogenRepository {
  Fragebogen getFragebogenById(Long id);

  List<Fragebogen> getAll();

  List<Fragebogen> getAllContaining(String search);

  void newFragebogen(Fragebogen fragebogen);

  List<Fragebogen> getAllFromStudentContaining(UUID id, String search);

  List<Fragebogen> getFromStudent(UUID id);
}
