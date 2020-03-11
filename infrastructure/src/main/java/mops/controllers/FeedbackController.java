package mops.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import mops.TypeChecker;
import mops.database.MockFragebogenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/feedback")
@Controller
public class FeedbackController {

  private static final String emptySearchString = "";
  private final transient FragebogenRepository frageboegen;
  private final transient TypeChecker typeChecker;

  public FeedbackController() {
    frageboegen = new MockFragebogenRepository();
    typeChecker = new TypeChecker();
  }

  String formatDate(LocalDateTime date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm:ss");

    String formatDateTime = date.format(formatter);
    return formatDateTime;

  }

  @GetMapping("/")
  public String uebersicht(Model model, String search) {
    if (!emptySearchString.equals(search) && (search != null)) {
      model.addAttribute("typeChecker", typeChecker);
      model.addAttribute("frageboegen", frageboegen.getAllContaining(search));
      model.addAttribute("numberofentries", 5);
      return "index";
    }
    //@TODO Law of Demeter FIX !!
    //FOR LOOP TO ITERATE  LIST OF FRAGEBOGEN
    long id = frageboegen.getFragebogenById(1L).getBogennr();
    LocalDateTime start = frageboegen.getFragebogenById(id).getStartdatum();
    LocalDateTime end = frageboegen.getFragebogenById(id).getEnddatum();
    model.addAttribute("startdate", formatDate(start));
    model.addAttribute("enddate", formatDate(end));
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
}
