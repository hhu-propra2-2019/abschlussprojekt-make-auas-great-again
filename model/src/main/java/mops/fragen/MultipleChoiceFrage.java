package mops.fragen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lombok.Getter;
import lombok.Setter;
import mops.antworten.Antwort;
import mops.antworten.MultipleChoiceAntwort;
import mops.antworten.MultipleResponseAntwort;

@Getter
@Setter
public class MultipleChoiceFrage extends Frage {
  private transient String fragentext;
  private transient List<Auswahl> choices;
  private boolean hasMultipleResponse;
  private List<Antwort> antworten;
  private Map<Auswahl, Double> auswertung = new HashMap<>();

  public MultipleChoiceFrage(Long id, String fragentext, boolean hasMultipleResponse) {
    super(id);
    this.fragentext = fragentext;
    this.choices = new ArrayList<>();
    this.hasMultipleResponse = hasMultipleResponse;
    this.antworten = new ArrayList<>();
  }

  public MultipleChoiceFrage(String fragentext) {
    super((long) new Random().nextInt(1000));
    this.fragentext = fragentext;
    this.choices = new ArrayList<>();
    fillDummyChoices();
    this.hasMultipleResponse = false;
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
    auswertung.put(choice, (double) 0);
  }

  public boolean containsChoice(String label) {
    return choices.stream()
        .anyMatch(choice -> choice.toString().equals(label));
  }

  public void deleteChoice(Long id) {
    Auswahl toRemove = choices.stream().filter(x -> x.getId().equals(id)).findFirst().get();
    choices.remove(toRemove);
  }

  public int getNumberOfChoices() {
    return choices.size();
  }

  @Override
  public void addAntwort(String antwort) {
    Auswahl auswahl = new Auswahl(antwort);
    if (choices.contains(auswahl)) {
      this.antworten.add(new MultipleChoiceAntwort((long) new Random().nextInt(1000), auswahl));
    }
    this.aktualisiereErgebnis();
  }

  @Override
  public List<Antwort> getAntworten() {
    return antworten;
  }

  @Override
  public String toString() {
    return fragentext;
  }

  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  private void aktualisiereErgebnis() {
    for (Auswahl auswahl : choices) {
      int anzahl = (int) antworten.stream()
          .map(x -> (MultipleResponseAntwort) x)
          .filter(x -> x.contains(auswahl))
          .count();
      auswertung.put(auswahl, berechneProzentualenAnteil(anzahl));
    }
  }

  private Double berechneProzentualenAnteil(int anzahl) {
    return (((double) anzahl) / antworten.size()) * 100;
  }

  public Double holeErgebnis(Auswahl auswahl) {
    return auswertung.get(auswahl);
  }
}
