package mops.fragen;

import java.util.List;
import mops.antworten.Antwort;

public class SingleResponseFrage extends MultipleChoiceFrage {

  public SingleResponseFrage(Long id, String fragentext) {
    super(id, fragentext);
  }

  public SingleResponseFrage(Long id, String fragentext, List<Auswahl> choices,
      List<Antwort> antworten) {
    super(id, fragentext, choices, antworten);
  }

  @Override
  public Frage clone() {
    return new SingleResponseFrage(getFragentext(), getChoices());
  }

  public SingleResponseFrage(String fragetext, List<Auswahl> choices) {
    super(fragetext, choices);
  }
}
