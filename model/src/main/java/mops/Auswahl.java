package mops;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "label")
@ToString // nur nötig für Debugging-Ausgabe im FeedbackController, kann später gelöscht werden
public class Auswahl {
  private String label;
}
