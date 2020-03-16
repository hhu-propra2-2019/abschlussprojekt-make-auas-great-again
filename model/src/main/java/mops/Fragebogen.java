package mops;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import mops.fragen.Frage;
import mops.fragen.MultipleChoiceFrage;
import mops.fragen.TextFrage;


@Builder
@EqualsAndHashCode(of = "bogennr")
@AllArgsConstructor
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

  public Fragebogen(String veranstaltung, String dozent, LocalDateTime start, LocalDateTime ende,
      Einheit einheit) {
    Random idgenerator = new Random();
    this.bogennr = idgenerator.nextLong();
    this.veranstaltungsname = veranstaltung;
    this.professorenname = dozent;
    this.startdatum = start;
    this.enddatum = ende;
    this.fragen = new ArrayList<>();
    this.type = einheit;
  }

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

  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  public List<TextFrage> getTextfragen() {
    List<TextFrage> textFragen = new ArrayList<>();
    for (Frage frage : fragen) {
      if (frage instanceof TextFrage) {
        textFragen.add((TextFrage) frage);
      }
    }
    return textFragen;
  }

  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  public List<MultipleChoiceFrage> getMultipleChoiceFragen() {
    List<MultipleChoiceFrage> mult = new ArrayList<>();
    for (Frage frage : fragen) {
      if (frage instanceof MultipleChoiceFrage) {
        mult.add((MultipleChoiceFrage) frage);
      }
    }
    return mult;
  }

  public void loescheFrage(Long id) {
    Optional<Frage> frage = fragen.stream().filter(x -> x.getId().equals(id)).findAny();
    frage.ifPresent(value -> fragen.remove(value));
  }
  
  public Frage getFrage(Long id) {
    Optional<Frage> frage = fragen.stream().filter(x -> x.getId().equals(id)).findFirst();
    return frage.get();
  }

  public void addFrage(Frage frage) {
    fragen.add(frage);
  }
}

