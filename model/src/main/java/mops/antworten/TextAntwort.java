package mops.antworten;

import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TextAntwort extends Antwort {
  private final Long id = Long.valueOf(new Random().nextInt(1000));
  private String antworttext;
}
