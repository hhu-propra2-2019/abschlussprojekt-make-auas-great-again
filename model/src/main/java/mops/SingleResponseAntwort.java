package mops;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleResponseAntwort extends Antwort {
  Integer antwort;

  public SingleResponseAntwort(Long id,Integer antwort) {
    super(id);
    this.antwort = antwort;
  }
}
