package mops;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Value;


@Builder
@Value
public class Formular {

  private String veranstaltungsname;
  private String professorenname;
  private List<Frage> fragen;
  private LocalDateTime startdatum;
  private LocalDateTime enddatum;

  /**
   * Gibt den Formularstatus zurück.
   *
   * @param heute als LocalDateTime
   * @return enum mops.Status ob VERFUEGBAR, NICHTVERFUEGBAR oder ABGESCHLOSSEN
   */
  public Status getStatus(LocalDateTime heute) {

    if (heute.isAfter(startdatum.minusDays(1)) && heute.isBefore(enddatum.plusDays(1))) {
      return Status.VERFUEGBAR;
    }
    return Status.NICHTVERFUEGBAR;
  }
}

