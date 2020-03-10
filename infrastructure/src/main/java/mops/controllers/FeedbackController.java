package mops.controllers;

import java.time.LocalDateTime;
import mops.Kontaktformular;
import mops.Student;
import mops.TypeChecker;
import mops.database.MockDozentenRepository;
import mops.database.MockFragebogenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/feedback")
@Controller
public class FeedbackController {

  private static final String emptySearchString = "";
  private final transient FragebogenRepository frageboegen;
  private final transient TypeChecker typeChecker;
  private transient DozentenRepository dozenten;


  public FeedbackController() {
    this.frageboegen = new MockFragebogenRepository();
    this.typeChecker = new TypeChecker();
    this.dozenten = new MockDozentenRepository();
  }

  @GetMapping("/")
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
    model.addAttribute("dozenten", dozenten.getAll());
    model.addAttribute("kontaktformular", new Kontaktformular());
    return "kontakt";
  }

  @PostMapping("/kontakt")
  public String postMessage(@ModelAttribute Kontaktformular kontakt, Model model) {
    kontakt.setStudent(new Student(678L, "a", "b", "a@b.de", 2278));
    kontakt.setZeitpunkt(LocalDateTime.now());
    return "index";
  }
}