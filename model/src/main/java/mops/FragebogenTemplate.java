package mops;

import java.util.ArrayList;
import java.util.List;
import lombok.Value;
import mops.fragen.Frage;

@Value
public class FragebogenTemplate {
  private final List<Frage> fragen = new ArrayList<>();

  public void addFrage(Frage frage) {
    fragen.add(frage);
  }
}
