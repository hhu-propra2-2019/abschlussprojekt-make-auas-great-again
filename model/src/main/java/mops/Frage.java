package mops;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
@EqualsAndHashCode(of = "id")
public abstract class Frage {

  @Getter
  private transient Long id;

  public Frage(Long id) {
    this.id = id;
  }
}
