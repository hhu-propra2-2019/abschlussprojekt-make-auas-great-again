package mops.controllers;

import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import mops.DateTimeService;
import mops.Einheit;
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
  private final transient DateTimeService datetime;

  public DozentController() {
    frageboegen = new MockFragebogenRepository();
    typechecker = new TypeChecker();
    datetime = new DateTimeService();
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
  public String addNeuesFormular(HttpServletRequest req, Model model) {
    Fragebogen neu = new Fragebogen(req.getParameter("veranstaltung"),
        req.getParameter("dozentname"),
        datetime.getLocalDateTimeFromString(req.getParameter("startdatum"),
            req.getParameter("startzeit")),
        datetime.getLocalDateTimeFromString(req.getParameter("enddatum"),
            req.getParameter("endzeit")),
        Einheit.valueOf(req.getParameter("veranstaltungstyp")));
    frageboegen.newFragebogen(neu);
    model.addAttribute("neuerbogen", neu);
    return "redirect:/feedback/dozenten/new";
  }
}
