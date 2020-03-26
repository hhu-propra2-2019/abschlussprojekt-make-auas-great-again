package mops.fragen;

import java.util.List;

public class SingleResponseFrage extends MultipleChoiceFrage {

  public SingleResponseFrage(Long id, String fragentext) {
    super(id, fragentext);
  }
  
  @Override
  public Frage clone() {
    return new SingleResponseFrage(this.getFragentext(), this.getChoices());
  }

  public SingleResponseFrage(String fragetext, List<Auswahl> choices) {
    super(fragetext, choices);
  }
}
