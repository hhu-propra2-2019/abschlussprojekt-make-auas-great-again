package mops.fragen;

import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(of = "label")
@AllArgsConstructor
public class Auswahl {
  private final Long id;
  @Setter
  private String label;
  @Setter
  private Integer value;

  public Auswahl(String label) {
    this.id = (long) new Random().nextInt(1000);
    this.label = label;
  }

  public Auswahl(String label, Integer value) {
    this.id = (long) new Random().nextInt(1000);
    this.label = label;
    this.value = value;
  }

  @Override
  public String toString() {
    return label;
  }
}
