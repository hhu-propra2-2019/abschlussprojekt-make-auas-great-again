package mops;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
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

  public Fragebogen(String veranstaltung, String dozent) {
    Random idgenerator = new Random();
    this.bogennr = idgenerator.nextLong();
    this.veranstaltungsname = veranstaltung;
    this.professorenname = dozent;
    this.startdatum = LocalDateTime.now().plusDays(1);
    this.enddatum = LocalDateTime.now().plusDays(8);
    this.fragen = new ArrayList<>();
    this.type = Einheit.VORLESUNG;
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

  public List<MultipleChoiceFrage> getMultipleChoiceFragen() {
    return fragen.stream()
        .filter(frage -> frage instanceof MultipleChoiceFrage)
        .map(frage -> (MultipleChoiceFrage) frage)
        .collect(Collectors.toList());
  }

  public List<TextFrage> getTextFragen() {
    return fragen.stream()
        .filter(frage -> frage instanceof TextFrage)
        .map(frage -> (TextFrage) frage)
        .collect(Collectors.toList());
  }

  public void loescheFrageById(Long id) {
    fragen.remove(new TextFrage(id, ""));
  }

  public void loescheFrage(Frage frage) {
    fragen.remove(frage);
  }

  public Frage getFrage(Long id) {
    Optional<Frage> frage = fragen.stream()
        .filter(x -> x.getId().equals(id))
        .findFirst();
    return frage.orElse(null);
  }

  public void addFrage(Frage frage) {
    fragen.add(frage);
  }
}