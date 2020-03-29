package mops.fragen;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mops.antworten.Antwort;

@NoArgsConstructor
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

  public abstract void addAntwort(Antwort antwort);

  public abstract List<Antwort> getAntworten();

  public abstract Frage klonen();

  @Override
  public abstract String toString();
}
