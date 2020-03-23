package mops.antworten;


import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import mops.fragen.Auswahl;

public class MultipleResponseAntwort extends MultipleChoiceAntwort {
  @Getter
  private transient List<Auswahl> gewaehlteAntworten;

  public MultipleResponseAntwort(Long id) {
    super(id);
    gewaehlteAntworten = new ArrayList<>();
  }

  public void addAntwort(Auswahl neueAntwort) {
    gewaehlteAntworten.add(neueAntwort);
  }

  @Override
  public String toString() {
    if (gewaehlteAntworten == null || gewaehlteAntworten.isEmpty()) {
      return "";
    }
    StringBuilder result = new StringBuilder();
    gewaehlteAntworten.forEach(antwort -> result.append(antwort.toString()).append(","));
    return result.substring(0, result.length() - 1);
  }

  public boolean contains(Auswahl auswahl) {
    return gewaehlteAntworten.stream()
        .anyMatch(choice -> choice.getLabel().equals(auswahl.getLabel()));
  }
}
