package mops;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings({"PMD.DataflowAnomalyAnalysis"})
public class MultipleChoiceFrage extends Frage {
  private transient String fragentext;
  private transient List<Auswahl> choices;
  private boolean hasMultipleResponse;
  private List<MultipleChoiceAntwort> antworten;
  private Map<Auswahl, Double> result = new HashMap<>();

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
    result.put(choice, Double.valueOf(0));
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
    this.aktualisiereErgebnis();
  }
  
  private void aktualisiereErgebnis() {
    for (Auswahl auswahl : choices) {
      long anzahl = antworten.stream().filter(x -> x.getAntwort().equals(auswahl)).count();
      result.put(auswahl, berechneProzent(anzahl));
    }
  }

  private Double berechneProzent(long anzahl) {
    return Double.valueOf((((double) anzahl) / antworten.size()) * 100);
  }
  
  public Double holeErgebnis(Auswahl auswahl) {
    return result.get(auswahl);
  }
}
