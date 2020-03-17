package mops.controllers;

import java.util.List;
import mops.Veranstaltung;

public interface VeranstaltungsRepository {

  List<Veranstaltung> getAll();

  List<Veranstaltung> getAllContaining(String search);

  List<Veranstaltung> getVeranstaltungById(Long id);

}
