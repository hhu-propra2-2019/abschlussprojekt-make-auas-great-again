package mops.fragen;

import java.util.List;
import mops.antworten.Antwort;

public class MultipleResponseFrage extends MultipleChoiceFrage {
  public MultipleResponseFrage(Long id, String fragentext) {
    super(id, fragentext);
  }

  public MultipleResponseFrage(String fragentext) {
    super(fragentext);
  }

  public MultipleResponseFrage(Long id, String fragentext, Boolean oeffentlich,
      List<Auswahl> choices, List<Antwort> antworten) {
    super(id, fragentext, oeffentlich, choices, antworten);
  }

  @Override
  public Frage klonen() {
    return new MultipleResponseFrage(getFragentext(), getChoices());
  }

  public MultipleResponseFrage(String fragetext, List<Auswahl> choices) {
    super(fragetext, choices);
  }
}
