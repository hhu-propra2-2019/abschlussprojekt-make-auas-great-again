package mops;

import lombok.Getter;
import lombok.Setter;

public class TextFrage extends Frage {
  @Getter
  @Setter
  private String antwort;

  public TextFrage(String frage) {
    super(frage);
  }
}
