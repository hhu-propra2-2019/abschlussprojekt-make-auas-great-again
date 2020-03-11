package mops.controllers;

import mops.TypeChecker;
import mops.database.MockFragebogenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

  @PostMapping("/ergebnis")
  public String waehleBogen() {
    return "ergebnis";
  }

  @GetMapping("/ergebnis/{bogennr}")
  public String ergebnisUebersicht() {
    return "ergebnis";
  }

}
