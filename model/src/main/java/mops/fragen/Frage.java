package mops.fragen;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import mops.antworten.Antwort;

@EqualsAndHashCode(of = "id")
public abstract class Frage {
  @Getter
  private transient Long id;
  private boolean isOeffentlich;

  public Frage(Long id) {
    this.id = id;
  }

  public void aendereOeffentlichkeitsStatus() {
    isOeffentlich = !isOeffentlich;
  }

  public boolean isOeffentlich() {
    return isOeffentlich;
  }

  public abstract void addAntwort(String antwort);

  public abstract Iterable<Antwort> getAntworten();

  public abstract String toString();

}
