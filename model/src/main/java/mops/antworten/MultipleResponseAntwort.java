package mops.antworten;


import java.util.HashSet;
import java.util.Set;

public class MultipleResponseAntwort extends Antwort {
  transient Set<Integer> gewaehlteAntworten;

  public MultipleResponseAntwort() {
    gewaehlteAntworten = new HashSet<>();
  }

  public void addAntwort(Integer neueAntwort) {
    gewaehlteAntworten.add(neueAntwort);
  }

}
