package mops;


import java.util.HashSet;
import java.util.Set;

public class MultipleResponseAntwort extends Antwort {
  transient Set<Integer> gewaehlteAntworten;

  public MultipleResponseAntwort(Long id) {
    super(id);
    gewaehlteAntworten = new HashSet<Integer>();
  }

  public void addAntwort(Integer neueAntwort) {
    gewaehlteAntworten.add(neueAntwort);
  }

}
