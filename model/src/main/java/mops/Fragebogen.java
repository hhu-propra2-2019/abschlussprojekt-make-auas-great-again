package mops;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Builder
@EqualsAndHashCode(of = "bogennr")
@Getter
@Setter
public class Fragebogen {

  private final Long bogennr;
  private String veranstaltungsname;
  private String professorenname;
  private List<Frage> fragen;
  private LocalDateTime startdatum;
  private LocalDateTime enddatum;
  private Einheit type;

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

  /**
   * Gibt die verbleibenden Studen bis zum auslaufen des Fragebogens zurück.
   *
   * @return String
   */
  public String getRemainingHours() {
    return "24";
  }

  /**
   * Gibt die verbleibenden Minuten bis zum auslaufen des Fragebogens zurück.
   * Rückgabe ist immer zwischen 0 und 59
   *
   * @return String
   */
  public String getRemainingMinutes() {
    return "59";
  }

  /**
   * Gibt die verbleibenden Studen bis zum auslaufen des Fragebogens zurück.
   * Rückgabe ist immer zwischen 0 und 59
   *
   * @return String
   */
  public String getRemainingSeconds() {
    return "59";
  }

  /**
   * Checked ob der Fragebogen den seach Parameter enthält.
   *
   * @param search Der Suchbegriff als String
   * @return True wenn der Suchbegriff gefunden wurde
   */
  public boolean contains(String search) {
    if (professorenname.toLowerCase(Locale.GERMAN).contains(search.toLowerCase(Locale.GERMAN))) {
      return true;
    } else {
      return veranstaltungsname.toLowerCase(Locale.GERMAN)
          .contains(search.toLowerCase(Locale.GERMAN));
    }
  }
}

