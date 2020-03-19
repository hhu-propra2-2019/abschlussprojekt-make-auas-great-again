package mops.fragen;

import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Value;
import mops.antworten.TextAntwort;

@Value
@EqualsAndHashCode(callSuper = true)
public class TextFrage extends Frage {
  private String fragentext;
  private Set<TextAntwort> antworten;

  public TextFrage(Long id, String fragentext) {
    super(id);
    this.fragentext = fragentext;
    this.antworten = new HashSet<>();
  }

  public TextFrage(String fragentext) {
    super((long) new Random().nextInt(1000));
    this.fragentext = fragentext;
    this.antworten = new HashSet<>();
  }

  @Override
  public void addAntwort(String text) {
    TextAntwort antwort = new TextAntwort(text);
    this.antworten.add(antwort);
  }

  public TextAntwort getAntwortById(Long id) {
    Optional<TextAntwort> antwort = antworten.stream().filter(x -> x.getId().equals(id))
        .findFirst();
    return antwort.get();
  }
}
