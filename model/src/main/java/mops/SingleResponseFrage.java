package mops;

public class SingleResponseFrage extends MultipleChoiceFrage {
  final transient boolean isScala;

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  public SingleResponseFrage(Long id, String fragentext, boolean isScala) {
    super(id, fragentext, false);
    this.isScala = isScala;

  }
}