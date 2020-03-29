package mops.antworten;

import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
public class TextAntwort extends Antwort {
  @Setter
  private transient String antworttext;

  public TextAntwort(Long id) {
    super(id);
  }

  public TextAntwort(Long id, String antworttext) {
    super(id);
    this.antworttext = antworttext;
  }

  @Override
  public String toString() {
    return antworttext;
  }
}
