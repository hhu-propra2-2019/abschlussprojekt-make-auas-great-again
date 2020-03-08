package mops;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MultipleChoiceFrage extends Frage {
  private transient String fragentext;
  private transient List<Auswahl> choices;

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  public MultipleChoiceFrage(Long id, String fragentext) {
    super(id);
    this.fragentext = fragentext;
    this.choices = new ArrayList<>();
  }

  public void addChoice(Auswahl choice) {
    this.choices.add(choice);
  }

  public int getNumberOfChoices() {
    return choices.size();
  }
}
