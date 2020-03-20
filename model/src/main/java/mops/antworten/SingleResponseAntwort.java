package mops.antworten;

public class SingleResponseAntwort extends Antwort {
  private transient Integer antwort;

  public SingleResponseAntwort(Long id) {
    super(id);
  }

  public SingleResponseAntwort(Long id, Integer antwort) {
    super(id);
    this.antwort = antwort;
  }

  @Override
  public String toString() {
    return antwort.toString();
  }
}
