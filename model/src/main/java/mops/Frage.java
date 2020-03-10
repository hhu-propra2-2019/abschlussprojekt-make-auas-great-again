package mops;

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

  public Frage(Long id) {
    this.id = id;
  }
}
