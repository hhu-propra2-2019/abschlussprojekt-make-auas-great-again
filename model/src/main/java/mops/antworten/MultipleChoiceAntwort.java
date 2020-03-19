package mops.antworten;

import mops.fragen.Auswahl;

public class MultipleChoiceAntwort extends Antwort {
  private Auswahl antwort;

  public MultipleChoiceAntwort(Long id, Auswahl auswahl) {
    super(id);
    this.antwort = auswahl;
  }

  public MultipleChoiceAntwort(Long id) {
    super(id);
  }

  @Override
  public String toString() {
    return antwort.toString();
  }
}
