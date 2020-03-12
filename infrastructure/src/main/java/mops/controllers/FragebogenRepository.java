package mops.controllers;

import java.time.LocalDateTime;
import java.util.List;
import mops.Fragebogen;
import mops.TextFrage;


public interface FragebogenRepository {
  Fragebogen getFragebogenById(Long id);

  List<Fragebogen> getAll();

  List<Fragebogen> getAllContaining(String search);

  void newFragebogen(Fragebogen fragebogen);
}
