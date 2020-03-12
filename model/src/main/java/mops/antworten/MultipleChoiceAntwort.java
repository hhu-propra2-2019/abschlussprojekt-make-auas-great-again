package mops.antworten;

import lombok.AllArgsConstructor;
import lombok.Value;
import mops.Auswahl;

@Value
@AllArgsConstructor
public class MultipleChoiceAntwort extends Antwort {
  private Auswahl antwort;
}
