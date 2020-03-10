package mops.services;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import mops.Auswahl;
import mops.Frage;
import mops.Fragebogen;
import mops.MultipleChoiceAntwort;
import mops.MultipleChoiceFrage;
import mops.TextAntwort;
import mops.TextFrage;
import mops.TypeChecker;
import mops.controllers.FragebogenRepository;

@Service
@AllArgsConstructor
public class SubmitService {
  private final transient FragebogenRepository frageboegen;

  public void saveAntworten(HttpServletRequest req, long bogennr) {
    Fragebogen fragebogen = frageboegen.getFragebogenById(bogennr);
    List<Frage> fragen = fragebogen.getFragen();
    for (Frage frage : fragen) {
      if (TypeChecker.isMultipleChoice(frage)) {
        saveMultipleChoiceAntwort(req, frage);
      } else if (TypeChecker.isTextFrage(frage)) {
        saveTextantwort(req, frage);
      }
    }
  }

  private void saveMultipleChoiceAntwort(HttpServletRequest req, Frage frage) {
    List<Auswahl> moeglicheantworten = ((MultipleChoiceFrage) frage).getChoices();
    Auswahl auswahl = new Auswahl(req.getParameter("answer-" + frage.getId()));
    if (moeglicheantworten.contains(auswahl)) {
      ((MultipleChoiceFrage) frage).addAntwort(new MultipleChoiceAntwort(auswahl));
      System.out.println("Antwort " + auswahl.toString() + " wurde zur Frage '"
          + frage.getFragentext() + "' hinzugefügt"); // Debugging-Ausgabe, kann später gelöscht
                                                      // werden
    }
  }

  private void saveTextantwort(HttpServletRequest req, Frage frage) {
    String antwort = req.getParameter("answer-" + frage.getId());
    ((TextFrage) frage).addAntwort(new TextAntwort(antwort));
    System.out.println("Antwort auf Frage '" + frage.getFragentext() + "': " + antwort); // Debugging-Ausgabe
  }
}
