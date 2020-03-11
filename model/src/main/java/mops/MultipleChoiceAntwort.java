package mops;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class MultipleChoiceAntwort extends Antwort {
  private Auswahl antwort;
}
