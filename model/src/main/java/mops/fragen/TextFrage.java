package mops.fragen;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import mops.antworten.Antwort;
import mops.antworten.TextAntwort;

public class TextFrage extends Frage {
  private transient String fragentext;
  private transient List<Antwort> antworten;

  public TextFrage(Long id, String fragentext) {
    super(id);
    this.fragentext = fragentext;
    antworten = new ArrayList<>();
  }

  public TextFrage(String fragentext) {
    super((long) new Random().nextInt(1000));
    this.fragentext = fragentext;
    antworten = new ArrayList<>();
  }

  @Override
  public void addAntwort(String text) {
    TextAntwort antwort = new TextAntwort((long) new Random().nextInt(1000), text);
    antworten.add(antwort);
  }

  @Override
  public void addAntwort(Antwort antwort) {
    antworten.add(antwort);
  }

  public void addAntwort(TextAntwort antwort) {
    antworten.add(antwort);
  }

  @Override
  public List<Antwort> getAntworten() {
    return antworten;
  }

  @Override
  public String toString() {
    return fragentext;
  }

  public Antwort getAntwortById(Long id) {
    Optional<Antwort> antwort = antworten.stream().filter(x -> x.getId().equals(id)).findFirst();
    return antwort.orElse(null);
  }
}
