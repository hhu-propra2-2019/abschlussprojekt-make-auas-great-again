package mops.fragen;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import mops.antworten.Antwort;
import mops.antworten.MultipleChoiceAntwort;

@Getter
@Setter
public class MultipleChoiceFrage extends Frage {
  public static final long ZERO = 0L;
  private transient String fragentext;
  private transient List<Auswahl> choices;
  private List<Antwort> antworten;

  public MultipleChoiceFrage(Long id, String fragentext) {
    super(id, false);
    this.fragentext = fragentext;
    this.choices = new ArrayList<>();
    this.antworten = new ArrayList<>();
  }

  public MultipleChoiceFrage(String fragentext) {
    this.fragentext = fragentext;
    this.choices = new ArrayList<>();
    this.antworten = new ArrayList<>();
  }
  
  public MultipleChoiceFrage(Long id, String fragentext, Boolean oeffentlich,
      List<Auswahl> choices, List<Antwort> antworten) {
    super(id, oeffentlich);
    this.fragentext = fragentext;
    this.choices = choices;
    this.antworten = antworten;
  }
  
  @Override
  public Frage klonen() {
    List<Auswahl> auswahlen =
        choices.stream().map(x -> x.klonen()).collect(Collectors.toList());
    return new MultipleChoiceFrage(fragentext, auswahlen);
  }
  
  public MultipleChoiceFrage(String fragentext, List<Auswahl> choices) {
    this.fragentext = fragentext;
    this.choices = new ArrayList<>();
    choices.stream().forEach(x -> this.addChoice(x));
    this.antworten = new ArrayList<>();
  }

  public void addChoice(Auswahl choice) {
    this.choices.add(choice);
  }

  public boolean containsChoice(String label) {
    return choices.stream()
        .anyMatch(choice -> choice.toString().equals(label));
  }

  public void deleteChoice(Long id) {
    Auswahl toRemove = choices
        .stream()
        .filter(x -> x.getId().equals(id))
        .findFirst()
        .orElse(new Auswahl(""));
    choices.remove(toRemove);
  }

  public int getNumberOfChoices() {
    return choices.size();
  }

  @Override
  public void addAntwort(String antwort) {
    Auswahl auswahl = new Auswahl(antwort);
    if (choices.contains(auswahl)) {
      MultipleChoiceAntwort neu = new MultipleChoiceAntwort(new ArrayList<>());
      neu.addAntwort(auswahl);
      this.antworten.add(neu);
    }
  }

  @Override
  public void addAntwort(Antwort antwort) {
    antworten.add(antwort);
  }

  @Override
  public List<Antwort> getAntworten() {
    return antworten;
  }

  @Override
  public String toString() {
    return fragentext;
  }

  private Double berechneProzentualenAnteil(long anzahl) {
    if (anzahl == ZERO) {
      return 0.0;
    }
    return (((double) anzahl) / antworten.size()) * 100;
  }

  public Double holeErgebnis(Auswahl auswahl) {
    long anzahl = antworten.stream()
        .map(Antwort::toString)
        .filter(str -> str.equals(auswahl.getLabel()))
        .count();
    return berechneProzentualenAnteil(anzahl);
  }
}
