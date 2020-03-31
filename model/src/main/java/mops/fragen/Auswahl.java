package mops.fragen;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(of = "label")
@AllArgsConstructor
public class Auswahl {
  private Long id;
  @Setter
  private String label;

  public Auswahl(String label) {
    this.label = label;
  }
  
  public Auswahl klonen() {
    return new Auswahl(this.label);
  }

  @Override
  public String toString() {
    return label;
  }
}
