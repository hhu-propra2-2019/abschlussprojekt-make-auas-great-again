package mops.antworten;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public abstract class Antwort {
  @Getter
  private Long id;

  public Antwort(Long id) {
    this.id = id;
  }

  @Override
  public abstract String toString();
}

