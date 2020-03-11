package mops;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MultipleChoiceFrage extends Frage {
  private transient String fragentext;
  private transient List<Auswahl> choices;
  private boolean hasMultipleResponse;
  private List<MultipleChoiceAntwort> antworten;
  private Map<Auswahl, Double> result;

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  public MultipleChoiceFrage(Long id, String fragentext, boolean hasMultipleResponse) {
    super(id);
    this.fragentext = fragentext;
    this.choices = new ArrayList<>();
    this.hasMultipleResponse = hasMultipleResponse;
    this.antworten = new ArrayList<>();
  }

  public void addChoice(Auswahl choice) {
    this.choices.add(choice);
  }

  public int getNumberOfChoices() {
    return choices.size();
  }
  
  @Override
  public void addAntwort(String antwort) {
    Auswahl auswahl = new Auswahl(antwort);
    if (choices.contains(auswahl)) {
      this.antworten.add(new MultipleChoiceAntwort(auswahl));
    }
  }
  
  public void berechneErgebnis() {
    result = new HashMap<>();
    for (Auswahl auswahl : choices) {
      long anzahl = antworten.stream().filter(x -> x.getAntwort().equals(auswahl)).count();
      result.put(auswahl, berechneProzent(anzahl));
    }
  }

  private Double berechneProzent(long anzahl) {
    if (antworten.size() != 0) {
      return Double.valueOf((((double) anzahl) / antworten.size()) * 100);
    } else {
      return Double.valueOf(0);
    }
  }
  
  public Double holeErgebnis(Auswahl auswahl) {
    return result.get(auswahl);
  }
}
