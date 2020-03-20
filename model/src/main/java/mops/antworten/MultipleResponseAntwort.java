package mops.antworten;


import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import mops.fragen.Auswahl;

public class MultipleResponseAntwort extends Antwort {
  @Getter
  private transient Set<Auswahl> gewaehlteAntworten;

  public MultipleResponseAntwort(Long id) {
    super(id);
    gewaehlteAntworten = new HashSet<>();
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
