package mops;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MultipleChoiceFrage extends Frage {
  private transient String fragentext;

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  public MultipleChoiceFrage(Long id, String fragentext) {
    super(id);
    this.fragentext = fragentext;
  }
}
