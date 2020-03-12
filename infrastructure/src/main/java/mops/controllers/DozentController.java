package mops.controllers;

import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import mops.Fragebogen;
import mops.TypeChecker;
import mops.database.MockFragebogenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/feedback/dozenten")
public class DozentController {
  private final transient FragebogenRepository frageboegen;
  private final transient TypeChecker typechecker;

  public DozentController() {
    frageboegen = new MockFragebogenRepository();
    typechecker = new TypeChecker();
  }

  @GetMapping("/")
  public String getOrganisatorMainPage() {
    return "dozenten/dozent";
  }

  @GetMapping("/watch")
  public String getFragebogenUebersicht(Model model) {
    List<Fragebogen> fragebogenliste = frageboegen.getAll();
    model.addAttribute("frageboegen", fragebogenliste);
    model.addAttribute("typechecker", typechecker);
    return "dozenten/frageboegen";
  }

  @GetMapping("/watch/{bogennr}")
  public String getAntwortenEinesFragebogens(@PathVariable long bogennr, Model model) {
    Fragebogen fragebogen = frageboegen.getFragebogenById(bogennr);
    model.addAttribute("fragebogen", fragebogen);
    model.addAttribute("typechecker", typechecker);
    return "dozenten/ergebnisse";
  }

  @GetMapping("/new")
  public String getNeueFormularSeite() {
    return "dozenten/ersteller";
  }

  @PostMapping("/new")
  public String addNeuesFormular(HttpServletRequest req) {
    String veranstaltung = req.getParameter("veranstaltung");
    String dozentname = req.getParameter("dozentname");
    String veranstaltungstyp = req.getParameter("veranstaltungstyp");
    String startdatum = req.getParameter("startdatum");
    String startzeit = req.getParameter("startzeit");
    String enddatum = req.getParameter("enddatum");
    return "redirect:/feedback/dozenten/new";
  }
}
