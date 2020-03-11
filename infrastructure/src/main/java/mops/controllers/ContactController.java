package mops.controllers;

import java.time.LocalDateTime;
import mops.Kontaktformular;
import mops.Student;
import mops.database.MockDozentenRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

public class ContactController {

  private transient DozentenRepository dozenten;

  public ContactController() {
    this.dozenten = new MockDozentenRepository();
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
