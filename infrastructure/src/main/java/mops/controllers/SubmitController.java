package mops.controllers;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import mops.Auswahl;
import mops.Frage;
import mops.Fragebogen;
import mops.MultipleChoiceAntwort;
import mops.MultipleChoiceFrage;
import mops.TextAntwort;
import mops.TextFrage;
import mops.TypeChecker;
import mops.services.SubmitService;

@RequestMapping("/feedback/details/submit")
@Controller
public class SubmitController {
  private final transient FragebogenRepository frageboegen;
  private final transient SubmitService service;

  public SubmitController(FragebogenRepository frageboegen, SubmitService service) {
    this.frageboegen = frageboegen;
    this.service = service;
  }

  @PostMapping("/{bogennr}")
  public String submitFeedback(Model model, @PathVariable long bogennr, HttpServletRequest req) {
    Fragebogen fragebogen = frageboegen.getFragebogenById(bogennr);
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
    return "redirect:/feedback/";
  }
}
