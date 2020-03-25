package mops.fragen;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import mops.antworten.Antwort;

@EqualsAndHashCode(of = "id")
public abstract class Frage {
  @Getter
  private transient Long id;
  private transient boolean oeffentlich;

  public Frage(Long id) {
    this.id = id;
  }

  public void aendereOeffentlichkeitsStatus() {
    oeffentlich = !oeffentlich;
  }

  public boolean isOeffentlich() {
    return oeffentlich;
  }

  public abstract void addAntwort(String antwort);

  public abstract Iterable<Antwort> getAntworten();
  
  public abstract Frage clone();

  @Override
  public abstract String toString();
}
