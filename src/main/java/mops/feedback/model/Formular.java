package mops.feedback.model;

import java.time.LocalDate;
import java.util.List;
import lombok.Value;

@Value
public class Formular {

  private String Veranstaltungsname;
  private String Professorenname;
  private List<Frage> fragen;
  private LocalDate startdatum;
  private LocalDate enddatum;

  public Status getStatus(LocalDate heute) {
    if (heute.isAfter(startdatum) && heute.isBefore(enddatum)) {
      return Status.VERFUEGBAR;
    }
    return Status.NICHTVERFUEGBAR;
  }
}
