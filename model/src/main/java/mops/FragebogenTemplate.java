package mops;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import mops.fragen.Frage;
import mops.fragen.MultipleChoiceFrage;
import mops.fragen.TextFrage;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "name")
public class FragebogenTemplate {
  private final Long id;
  private final String name;
  private final List<Frage> fragen;

  public FragebogenTemplate(String name) {
    Random idgenerator = new Random();
    this.id = (long) idgenerator.nextInt(2000000000);
    this.name = name;
    this.fragen = new ArrayList<>();
  }

  public void addFrage(Frage frage) {
    fragen.add(frage);
  }

  public void deleteFrageById(Long id) {
    fragen.remove(fragen.stream().filter(x -> x.getId().equals(id)).findFirst()
        .orElse(new TextFrage(id, "")));
  }

  public MultipleChoiceFrage getMultipleChoiceFrageById(Long id) {
    return (MultipleChoiceFrage) fragen.stream().filter(x -> x.getId().equals(id))
        .findFirst().orElse(new MultipleChoiceFrage(id, ""));
  }
}
