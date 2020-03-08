package mops.controllers;

import java.util.List;
import mops.Fragebogen;


public interface FragebogenRepository {
  Fragebogen getFragebogenById(Long id);

  List<Fragebogen> getAll();

  List<Fragebogen> getAllContaining(String search);
}
