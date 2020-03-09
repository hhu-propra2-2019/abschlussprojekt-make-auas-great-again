package mops;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SingleResponseAntwort extends Antwort {
  Integer antwort;

  public SingleResponseAntwort(Long id,Integer antwort) {
    super(id);
    this.antwort = antwort;
  }
}
