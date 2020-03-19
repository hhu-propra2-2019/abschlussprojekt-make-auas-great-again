package mops.antworten;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(of = "id")
public abstract class Antwort {
  @Getter
  private Long id;

  public Antwort(Long id) {
    this.id = id;
  }

  public abstract String toString();
}

