package mops;

import java.util.HashSet;
import java.util.Set;
import lombok.Value;

@Value
public class TextFrage extends Frage {
  private String fragentext;
  private Set<TextAntwort> antworten;

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  public TextFrage(Long id, String frage) {
    super(id);
    this.fragentext = frage;
    this.antworten = new HashSet<>();
  }
  
  public void addAntwort(String text) {
    TextAntwort antwort = new TextAntwort(text);
    this.antworten.add(antwort);
  }
}
