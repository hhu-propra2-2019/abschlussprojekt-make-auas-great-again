package mops.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import mops.TypeChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/feedback")
@Controller
public class FeedbackController {

  private static final String emptySearchString = "";
  @Autowired
  @Qualifier("Faker")
  private transient FragebogenRepository frageboegen;

  private transient TypeChecker typeChecker = new TypeChecker();

  @SuppressWarnings("checkstyle:MissingJavadocMethod")

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

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
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

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  @PostMapping("/kontakt")
  public String postMessage(Model model) {
    model.addAttribute("post", "post");
    model.addAttribute("submit", "submit");
    return "kontakt";
  }

}


