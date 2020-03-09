package mops;

public class SingleResponseFrage extends MultipleChoiceFrage {
  private final boolean isScala;

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  public SingleResponseFrage(Long id, String fragentext, boolean isScala) {
    super(id, fragentext, false);
    this.isScala = isScala;

  }
}
