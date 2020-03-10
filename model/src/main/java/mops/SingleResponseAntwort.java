package mops;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class SingleResponseAntwort extends Antwort {
  private Integer antwort;
}
