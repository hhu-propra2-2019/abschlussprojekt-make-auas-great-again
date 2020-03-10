package mops.controllers;

import mops.TypeChecker;
import mops.database.MockFragebogenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/feedback")
@Controller
public class FeedbackController {

  private static final String emptySearchString = "";
  private final transient FragebogenRepository frageboegen;
  private final transient TypeChecker typeChecker;


  public FeedbackController() {
    this.frageboegen = new MockFragebogenRepository();
    this.typeChecker = new TypeChecker();
  }

  @GetMapping("/uebersicht")
  public String uebersicht(Model model, String search) {
    if (!emptySearchString.equals(search) && search != null) {
      model.addAttribute("typeChecker", typeChecker);
      model.addAttribute("frageboegen", frageboegen.getAllContaining(search));
      return "index";
    }
    model.addAttribute("typeChecker", typeChecker);
    model.addAttribute("frageboegen", frageboegen.getAll());
    return "index";
  }

  @GetMapping("/details")
  public String fragebogen(Model model, @RequestParam Long id) {
    model.addAttribute("fragebogen", frageboegen.getFragebogenById(id));
    model.addAttribute("typeChecker", typeChecker);
    return "details";
  }

  @GetMapping("/kontakt")
  public String kontakt(Model model) {
    return "kontakt";
  }

  @PostMapping("/kontakt")
  public String postMessage(Model model) {
    model.addAttribute("post", "post");
    model.addAttribute("submit", "submit");
    return "kontakt";
  }
}