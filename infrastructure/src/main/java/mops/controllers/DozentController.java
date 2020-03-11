package mops.controllers;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import mops.ErgebnisService;
import mops.Fragebogen;
import mops.TypeChecker;
import mops.database.MockFragebogenRepository;

@Controller
@RequestMapping("/feedback/dozenten")
public class DozentController {
  private final transient FragebogenRepository frageboegen;
  private final transient ErgebnisService service;
  private final transient TypeChecker typechecker;

  public DozentController() {
    frageboegen = new MockFragebogenRepository();
    service = new ErgebnisService();
    typechecker = new TypeChecker();
  }

  @GetMapping("/")
  public String getOrganisatorMainPage(Model model) {
    List<Fragebogen> fragebogenliste = frageboegen.getAll();
    model.addAttribute("frageboegen", fragebogenliste);
    model.addAttribute("typechecker", typechecker);
    return "dozent";
  }

  @GetMapping("/watch/{bogennr}")
  public String getAntwortenEinesFragebogens(@PathVariable long bogennr, Model model) {
    Fragebogen fragebogen = frageboegen.getFragebogenById(bogennr);
    model.addAttribute("fragebogen", service.berechneErgebnisse(fragebogen));
    model.addAttribute("typechecker", typechecker);
    return "ergebnisse";
  }

}
