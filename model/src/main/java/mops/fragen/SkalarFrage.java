package mops.fragen;

import lombok.Getter;
import lombok.Setter;
import mops.Frage;

@Getter
@Setter
public class SkalarFrage extends Frage {
  private String fragentext;

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  public SkalarFrage(Long id, String fragentext) {
    super(id);
    this.fragentext = fragentext;
  }

  @Override
  public void addAntwort(String antwort) {

  }
}
