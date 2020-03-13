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

  public abstract void addAntwort(String antwort);

  public Frage(Long id) {
    this.id = id;
  }
}
