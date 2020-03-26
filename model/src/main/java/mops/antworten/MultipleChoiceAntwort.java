package mops.antworten;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import mops.fragen.Auswahl;

public class MultipleChoiceAntwort extends Antwort {
  @Getter
  private transient List<Auswahl> gewaehlteAntworten;

  public MultipleChoiceAntwort(Long id) {
    super(id);
    this.gewaehlteAntworten = new ArrayList<>();
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

  public void addAntwort(Auswahl auswahl) {
    gewaehlteAntworten.add(auswahl);
  }

  public void addAntworten(Set<Auswahl> ausgewaehlt) {
    gewaehlteAntworten.addAll(ausgewaehlt);
  }
}
