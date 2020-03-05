import java.time.LocalDateTime;
import java.util.List;
import lombok.Value;

@Value
public class Formular {

  private String veranstaltungsname;
  private String professorenname;
  private List<Frage> fragen;
  private LocalDateTime startdatum;
  private LocalDateTime enddatum;

  /**
   * gibt uns den Status von dem Formular.
   * @param  heute als LocalDateTime
   * @return enum Status ob VERFUEGBAR, NICHTVERFUEGBAR oder ABGESCHLOSSEN
   */
  public Status getStatus(LocalDateTime heute) {

    if (heute.isAfter(startdatum.minusDays(1)) && heute.isBefore(enddatum.plusDays(1))) {
      return Status.VERFUEGBAR;
    }
    return Status.NICHTVERFUEGBAR;
  }
}

