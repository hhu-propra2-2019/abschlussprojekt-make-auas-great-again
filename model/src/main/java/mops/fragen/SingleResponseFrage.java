package mops.fragen;

import java.util.List;

public class SingleResponseFrage extends MultipleChoiceFrage {

  final transient boolean isScala;

  public SingleResponseFrage(Long id, String fragentext, boolean isScala) {
    super(id, fragentext, false);
    this.isScala = isScala;
  }
  
  @Override
  public Frage clone() {
    return new SingleResponseFrage(this.getFragentext(), this.getChoices());
  }

  public SingleResponseFrage(String fragetext, List<Auswahl> choices) {
    super(fragetext, choices);
    isScala = false;
  }
}
