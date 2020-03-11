package mops;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MultipleChoiceFrage extends Frage {
  private transient String fragentext;
  private transient List<Auswahl> choices;
  private boolean hasMultipleResponse;
  private Set<MultipleChoiceAntwort> antworten;

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  public MultipleChoiceFrage(Long id, String fragentext, boolean hasMultipleResponse) {
    super(id);
    this.fragentext = fragentext;
    this.choices = new ArrayList<>();
    this.hasMultipleResponse = hasMultipleResponse;
    this.antworten = new HashSet<>();
  }

  public void addChoice(Auswahl choice) {
    this.choices.add(choice);
  }

  public int getNumberOfChoices() {
    return choices.size();
  }
  
  @Override
  public void addAntwort(String antwort) {
    Auswahl auswahl = new Auswahl(antwort);
    if (choices.contains(auswahl)) {
      this.antworten.add(new MultipleChoiceAntwort(auswahl));
    }
  }
}
