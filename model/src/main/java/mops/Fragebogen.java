package mops;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;


@Builder
@Value
@EqualsAndHashCode(of = "bogennr")
public class Fragebogen {

  Long bogennr;
  String veranstaltungsname;
  String professorenname;
  List<Frage> fragen;
  LocalDateTime startdatum;
  LocalDateTime enddatum;

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

