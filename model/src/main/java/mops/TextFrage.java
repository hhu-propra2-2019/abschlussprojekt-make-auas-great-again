package mops;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TextFrage extends Frage {
  private String fragentext;

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  public TextFrage(Long id, String frage) {
    super(id);
    this.fragentext = frage;
  }
}
