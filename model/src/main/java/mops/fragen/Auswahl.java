package mops.fragen;

import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "label")
public class Auswahl {
  private final Long id = Long.valueOf(new Random().nextInt(1000));
  private String label;
}
