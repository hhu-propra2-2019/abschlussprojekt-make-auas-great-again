package mops.fragen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
    super(id);
    this.fragentext = fragentext;
    this.choices = new ArrayList<>();
    this.antworten = new ArrayList<>();
  }

  public MultipleChoiceFrage(String fragentext) {
    super((long) new Random().nextInt(1000));
    this.fragentext = fragentext;
    this.choices = new ArrayList<>();
    fillDummyChoices();
    this.antworten = new ArrayList<>();
  }
  
  public MultipleChoiceFrage(Long id, String fragentext, List<Auswahl> choices, List<Antwort> antworten) {
    super(id);
    this.fragentext = fragentext;
    this.choices = choices;
    this.antworten = antworten;
  }
  
  @Override
  public Frage clone() {
    return new MultipleChoiceFrage(fragentext, choices);
  }
  
  public MultipleChoiceFrage(String fragentext, List<Auswahl> choices) {
    super((long) new Random().nextInt(1000));
    this.fragentext = fragentext;
    this.choices = new ArrayList<>();
    choices.stream().forEach(x -> this.addChoice(x));
    this.antworten = new ArrayList<>();
  }

  private void fillDummyChoices() {
    this.addChoice(new Auswahl("Trifft voll und ganz zu"));
    this.addChoice(new Auswahl("Trifft zu"));
    this.addChoice(new Auswahl("Trifft eher nicht zu"));
    this.addChoice(new Auswahl("Trifft überhaupt nicht zu"));
  }

  public void addChoice(Auswahl choice) {
    this.choices.add(choice);
  }

  public boolean containsChoice(String label) {
    return choices.stream()
        .anyMatch(choice -> choice.toString().equals(label));
  }

  public void deleteChoice(Long id) {
    Auswahl toRemove = choices.stream().filter(x -> x.getId().equals(id)).findFirst()
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
      MultipleChoiceAntwort neu = new MultipleChoiceAntwort((long) new Random().nextInt(1000));
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
