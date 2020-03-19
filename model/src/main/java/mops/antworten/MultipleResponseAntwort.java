package mops.antworten;


import java.util.HashSet;
import java.util.Set;

public class MultipleResponseAntwort extends Antwort {
  transient Set<Integer> gewaehlteAntworten;

  public MultipleResponseAntwort(Long id) {
    super(id);
    gewaehlteAntworten = new HashSet<>();
  }

  public void addAntwort(Integer neueAntwort) {
    gewaehlteAntworten.add(neueAntwort);
  }

  @Override
  public String toString() {
    if (gewaehlteAntworten.isEmpty()) {
      return "";
    }
    StringBuilder result = new StringBuilder();
    for (Integer antwort : gewaehlteAntworten) {
      result.append(antwort).append(",");
    }
    return result.substring(0, result.length() - 1);
  }
}
