package mops.fragen;

import java.util.List;

public class SingleResponseFrage extends MultipleChoiceFrage {

  final transient boolean isScala;

  public SingleResponseFrage(Long id, String fragentext, boolean isScala) {
    super(id, fragentext, false);
    this.isScala = isScala;
  }

  public SingleResponseFrage(String fragetext, List<Auswahl> choices) {
    super(fragetext, choices);
    isScala = false;
  }
}
