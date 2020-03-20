package mops.fragen;


public class SingleResponseFrage extends MultipleChoiceFrage {

  final transient boolean isScala;

  public SingleResponseFrage(Long id, String fragentext, boolean isScala) {
    super(id, fragentext, false);
    this.isScala = isScala;
  }
}
