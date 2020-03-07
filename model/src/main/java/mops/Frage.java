package mops;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "frage")
public abstract class Frage {
  private String frage;

  public Frage(String frage) {
    this.frage = frage;
  }
}
