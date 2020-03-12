package mops.controllers;

import java.time.LocalDateTime;
import java.util.List;
import mops.Fragebogen;
import mops.fragen.SkalarFrage;
import mops.fragen.TextFrage;


public interface FragebogenRepository {
  Fragebogen getFragebogenById(Long id);

  List<Fragebogen> getAll();

  List<Fragebogen> getAllContaining(String search);

  void changeDateById(Long formId, LocalDateTime startDate, LocalDateTime endDate);

  void addTextFrage(Long id, TextFrage frage);

  void addSkalarFrage(Long id, SkalarFrage frage);

  void deleteFrageByIdAndFrageId(Long formId, Long frageId);
}
