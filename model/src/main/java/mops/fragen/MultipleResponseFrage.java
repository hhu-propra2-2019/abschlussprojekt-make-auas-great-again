package mops.fragen;

import java.util.List;

public class MultipleResponseFrage extends MultipleChoiceFrage {


  public MultipleResponseFrage(Long id, String fragentext) {

    super(id, fragentext);
  }
  
  @Override
  public Frage clone() {
    return new MultipleResponseFrage(this.getFragentext(), this.getChoices());
  }

  public MultipleResponseFrage(String fragetext, List<Auswahl> choices) {
    super(fragetext, choices);
  }
}
