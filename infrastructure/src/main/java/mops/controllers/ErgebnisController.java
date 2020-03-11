package mops.controllers;

import java.util.ArrayList;
import java.util.List;
import mops.Frage;
import mops.Fragebogen;
import mops.MultipleChoiceFrage;
import mops.TextFrage;
import mops.TypeChecker;
import mops.database.MockFragebogenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/feedback")
@Controller
public class ErgebnisController {

  private final transient FragebogenRepository frageboegen;
  private final transient TypeChecker typeChecker;

  public ErgebnisController() {
    this.frageboegen = new MockFragebogenRepository();
    this.typeChecker = new TypeChecker();
  }

  @GetMapping("/ergebnis")
  public String boegenUebersicht(Model model) {
    model.addAttribute("frageboegen", frageboegen.getAll());
    model.addAttribute("typeChecker", typeChecker);
    return "ergebnis";
  }


  @GetMapping("/ergebnisUebersicht")
  public String ergebnisUebersicht(@RequestParam long id, Model model) {
    Fragebogen fragebogen = frageboegen.getFragebogenById(id);
    model.addAttribute("fragebogen", fragebogen);
    List<Frage> fragen = fragebogen.getFragen();
    List<MultipleChoiceFrage> multipleChoiceFragen = new ArrayList<>();
    List<TextFrage> textFragen = new ArrayList<>();
    for (Frage frage : fragen) {
      if (frage instanceof TextFrage) {
        textFragen.add((TextFrage) frage);
      } else {
        multipleChoiceFragen.add((MultipleChoiceFrage) frage);
      }
    }
    model.addAttribute("textFragen", textFragen);
    model.addAttribute("multipleChoiceFragen", multipleChoiceFragen);
    return "ergebnisUebersicht";
  }

}
