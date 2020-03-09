package mops;


import lombok.EqualsAndHashCode;
import lombok.Getter;

@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
@EqualsAndHashCode(of = "id")
public abstract class Antwort {

  @Getter
  private transient Long id;

  public Antwort(Long id) {
    this.id = id;
  }
}
