package mops.controllers;

import java.util.List;
import mops.Veranstaltung;

public interface VeranstaltungsRepository {

  List<Veranstaltung> getAll();

  List<Veranstaltung> getAllContaining(String search);

  Veranstaltung getVeranstaltungById(Long id);

}
