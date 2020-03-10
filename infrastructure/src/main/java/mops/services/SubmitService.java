package mops.services;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import mops.Auswahl;
import mops.Frage;
import mops.Fragebogen;
import mops.MultipleChoiceAntwort;
import mops.MultipleChoiceFrage;
import mops.TextAntwort;
import mops.TextFrage;
import mops.TypeChecker;

@Service
public class SubmitService {

  public void saveAntworten(HttpServletRequest req, Fragebogen fragebogen) {
    List<Frage> fragen = fragebogen.getFragen();
    for (Frage frage : fragen) {
      if (TypeChecker.isMultipleChoice(frage)) {
        List<Auswahl> moeglicheantworten = ((MultipleChoiceFrage) frage).getChoices();
        Auswahl auswahl = new Auswahl(req.getParameter("answer-" + frage.getId()));
        if (moeglicheantworten.contains(auswahl)) {
          ((MultipleChoiceFrage) frage).addAntwort(new MultipleChoiceAntwort(auswahl));
          System.out.println("Antwort " + auswahl.toString() + " wurde zur Frage '"
              + frage.getFragentext() + "' hinzugefügt"); // Debugging-Ausgabe, kann später gelöscht
                                                          // werden
        }
      } else if (TypeChecker.isTextFrage(frage)) {
        String antwort = req.getParameter("answer-" + frage.getId());
        ((TextFrage) frage).addAntwort(new TextAntwort(antwort));
        System.out.println("Antwort auf Frage '" + frage.getFragentext() + "': " + antwort); // Debugging-Ausgabe
      }
    }
  }
}
