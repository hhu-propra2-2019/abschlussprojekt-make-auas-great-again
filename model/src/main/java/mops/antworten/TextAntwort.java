package mops.antworten;

import lombok.AllArgsConstructor;
import lombok.Value;
import mops.antworten.Antwort;

@Value
@AllArgsConstructor
public class TextAntwort extends Antwort {
  private String antworttext;
}
