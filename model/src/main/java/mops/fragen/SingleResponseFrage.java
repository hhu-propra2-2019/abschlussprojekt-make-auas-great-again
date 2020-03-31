package mops.fragen;

import java.util.List;
import java.util.stream.Collectors;
import mops.antworten.Antwort;

public class SingleResponseFrage extends MultipleChoiceFrage {

  public SingleResponseFrage(Long id, String fragentext) {
    super(id, fragentext);
  }

  public SingleResponseFrage(String fragentext) {
    super(fragentext);
  }

  public SingleResponseFrage(Long id, String fragentext, Boolean oeffentlich,
      List<Auswahl> choices, List<Antwort> antworten) {
    super(id, fragentext, oeffentlich, choices, antworten);
  }

  @Override
  public Frage klonen() {
    List<Auswahl> auswahlen =
        getChoices().stream().map(x -> x.klonen()).collect(Collectors.toList());
    return new SingleResponseFrage(getFragentext(), auswahlen);
  }

  public SingleResponseFrage(String fragetext, List<Auswahl> choices) {
    super(fragetext, choices);
  }
}
