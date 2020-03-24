package mops;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import mops.fragen.Frage;

@Getter
@EqualsAndHashCode(of = "name")
@AllArgsConstructor
public class FragebogenTemplate {
  private final Long id = new Random().nextLong();
  private final String name;
  private final List<Frage> fragen = new ArrayList<>();

  public void addFrage(Frage frage) {
    fragen.add(frage);
  }
}
