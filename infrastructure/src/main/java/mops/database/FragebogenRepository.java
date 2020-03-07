package mops.database;

import java.util.List;
import mops.Fragebogen;


public interface FragebogenRepository {
  Fragebogen getFragebogenById(Long id);

  List<Fragebogen> getAll();
}
