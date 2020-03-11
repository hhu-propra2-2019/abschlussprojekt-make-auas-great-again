package mops.controllers;

import java.util.List;
import mops.Dozent;

public interface DozentenRepository {
  public List<Dozent> getAll();

  public Dozent getDozentById(Long id);
}
