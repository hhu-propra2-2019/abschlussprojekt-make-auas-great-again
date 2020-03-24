package mops;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import mops.fragen.Frage;
import mops.fragen.MultipleChoiceFrage;

@Getter
@EqualsAndHashCode(of = "name")
public class FragebogenTemplate {
  private final Long id;
  private final String name;
  private final List<Frage> fragen = new ArrayList<>();
  
  public FragebogenTemplate(String name) {
    Random idgenerator = new Random();
    this.id = idgenerator.nextLong();
    this.name = name;
  }

  public void addFrage(Frage frage) {
    fragen.add(frage);
  }
  
  public void deleteFrageById(Long id) {
    fragen.remove(fragen.stream().filter(x -> x.getId().equals(id)).findFirst().get());
  }
  
  public MultipleChoiceFrage getMultipleChoiceFrageById(Long id) {
    return (MultipleChoiceFrage) fragen.stream().filter(x -> x.getId().equals(id))
        .findFirst().get();
  }
}
