package mops.antworten;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class TextAntwort extends Antwort {
  private String antworttext;
}
