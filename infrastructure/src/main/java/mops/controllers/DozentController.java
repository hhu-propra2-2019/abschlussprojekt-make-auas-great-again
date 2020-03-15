package mops.controllers;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import mops.DateTimeService;
import mops.Einheit;
import mops.Fragebogen;
import mops.TypeChecker;
import mops.database.MockFragebogenRepository;
import mops.fragen.MultipleChoiceFrage;
import mops.fragen.TextFrage;


@Controller
@RequestMapping("/feedback/dozenten")
public class DozentController {
  private static final String REDIRECT_FEEDBACK_DOZENTEN_NEW_QUESTIONS = "redirect:/feedback/dozenten/new/questions/";
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
    return REDIRECT_FEEDBACK_DOZENTEN_NEW_QUESTIONS + neu.getBogennr();
  }

  @GetMapping("/new/questions/{bogennr}")
  public String seiteUmFragenHinzuzufuegen(@PathVariable Long bogennr, Model model) {
    model.addAttribute("neuerbogen", frageboegen.getFragebogenById(bogennr));
    return "dozenten/fragenerstellen";
  }

  @PostMapping("/new/questions/delete/{bogennr}/{fragennr}")
  public String loescheFrageAusFragebogen(@PathVariable Long bogennr, @PathVariable Long fragennr) {
    Fragebogen bogen = frageboegen.getFragebogenById(bogennr);
    bogen.loescheFrage(fragennr);
    return REDIRECT_FEEDBACK_DOZENTEN_NEW_QUESTIONS + bogennr;
  }

  @PostMapping("/new/questions/add/textfrage/{bogennr}")
  public String addTextfrage(@PathVariable Long bogennr, String fragetext) {
    TextFrage neuefrage = new TextFrage(fragetext);
    Fragebogen bogen = frageboegen.getFragebogenById(bogennr);
    bogen.addFrage(neuefrage);
    return REDIRECT_FEEDBACK_DOZENTEN_NEW_QUESTIONS + bogennr;
  }

  @PostMapping("/new/questions/add/mcfrage/{bogennr}")
  public String addMultipleChoiceFrage(@PathVariable Long bogennr, String fragemc) {
    MultipleChoiceFrage neuefrage = new MultipleChoiceFrage(fragemc);
    Fragebogen bogen = frageboegen.getFragebogenById(bogennr);
    bogen.addFrage(neuefrage);
    return REDIRECT_FEEDBACK_DOZENTEN_NEW_QUESTIONS + bogennr;
  }
}
