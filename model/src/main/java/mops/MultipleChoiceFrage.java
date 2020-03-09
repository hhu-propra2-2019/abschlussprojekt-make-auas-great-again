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
  private final boolean hasMultipleResponse;

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  public MultipleChoiceFrage(Long id, String fragentext,boolean hasMultipleResponse) {
    super(id);
    this.fragentext = fragentext;
    this.choices = new ArrayList<>();
    this.hasMultipleResponse = hasMultipleResponse;
  }

  public void addChoice(Auswahl choice) {
    this.choices.add(choice);
  }

  public int getNumberOfChoices() {
    return choices.size();
  }
}
