package mops;

import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import mops.fragen.Frage;

@Getter
@EqualsAndHashCode(of = "name")
public class FragebogenTemplate {
  @Setter
  private String name;
  private final List<Frage> fragen = new ArrayList<>();

  public void addFrage(Frage frage) {
    fragen.add(frage);
  }
}
