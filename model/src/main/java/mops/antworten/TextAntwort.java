package mops.antworten;

import lombok.Setter;

public class TextAntwort extends Antwort {
  @Setter
  private String antworttext;

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
