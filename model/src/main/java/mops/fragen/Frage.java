package mops.fragen;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
@EqualsAndHashCode(of = "id")
public abstract class Frage {
  @Getter
  private String fragentext;
  @Getter
  @Setter
  private transient Long id;

  @Getter
  private Boolean ergebnissesindoeffentlich = Boolean.valueOf(false);

  public void aendereOeffentlichkeitsStatus() {
    if (ergebnissesindoeffentlich) {
      ergebnissesindoeffentlich = Boolean.valueOf(false);
    } else {
      ergebnissesindoeffentlich = Boolean.valueOf(true);
    }
  }

  public abstract void addAntwort(String antwort);

  public Frage(Long id) {
    this.id = id;
  }
}
