package mops.feedback.model;

import java.time.LocalDate;
import java.util.List;
import lombok.Value;

@Value
public class Formular {

  private String nameVeranstaltung;
  private String nameProfessor;
  private List<Frage> fragen;
  private LocalDate startdatum;
  private LocalDate enddatum;
  private Status status;
}
