package mops.antworten;

import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(of = "id")
@Getter
@AllArgsConstructor
public class TextAntwort extends Antwort {
  private final Long id = Long.valueOf(new Random().nextInt(1000));
  @Setter
  private String antworttext;
}
