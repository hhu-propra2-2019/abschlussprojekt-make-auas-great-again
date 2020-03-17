package mops.antworten;

import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class TextAntwort extends Antwort {
  private final Long id = Long.valueOf(new Random().nextInt(1000));
  private String antworttext;
}
