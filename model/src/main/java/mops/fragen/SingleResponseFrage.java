package mops.fragen;

import java.util.List;

public class SingleResponseFrage extends MultipleChoiceFrage {

  public SingleResponseFrage(Long id, String fragentext) {
    super(id, fragentext);
  }

  public SingleResponseFrage(String fragetext, List<Auswahl> choices) {
    super(fragetext, choices);
  }
}
