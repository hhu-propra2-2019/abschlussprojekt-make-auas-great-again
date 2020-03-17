package mops.controllers;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import mops.DateTimeService;
import mops.Einheit;
import mops.Fragebogen;
import mops.TypeChecker;
import mops.antworten.TextAntwort;
import mops.database.MockFragebogenRepository;
import mops.fragen.Auswahl;
import mops.fragen.Frage;
import mops.fragen.MultipleChoiceFrage;
import mops.fragen.TextFrage;
import mops.security.Account;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/feedback/dozenten")
public class DozentController {
  public static final String orgaRole = "ROLE_orga";
  public static final String account = "account";
  private static final String REDIRECT_FEEDBACK_DOZENTEN_NEW_QUESTIONS =
      "redirect:/feedback/dozenten/new/questions/";
  private final transient FragebogenRepository frageboegen;
  private final transient TypeChecker typechecker;
  private final transient DateTimeService datetime;

  public DozentController() {
    frageboegen = new MockFragebogenRepository();
    typechecker = new TypeChecker();
    datetime = new DateTimeService();
  }

  @GetMapping("")
  @RolesAllowed(orgaRole)
  public String getOrganisatorMainPage() {
    return "dozenten/dozent";
  }

  @GetMapping("/watch")
  @RolesAllowed(orgaRole)
  public String getFragebogenUebersicht(KeycloakAuthenticationToken token, Model model) {
    List<Fragebogen> fragebogenliste = frageboegen.getAll();
    model.addAttribute("frageboegen", fragebogenliste);
    model.addAttribute("typechecker", typechecker);
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "dozenten/frageboegen";
  }

  @GetMapping("/watch/{bogennr}")
  @RolesAllowed(orgaRole)
  public String getAntwortenEinesFragebogens(KeycloakAuthenticationToken token,
      @PathVariable long bogennr, Model model) {
    Fragebogen fragebogen = frageboegen.getFragebogenById(bogennr);
    model.addAttribute("fragebogen", fragebogen);
    model.addAttribute("typechecker", typechecker);
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "dozenten/ergebnisse";
  }

  @GetMapping("/watch/edit/{bogennr}/{fragennr}/{antwortnr}")
  @RolesAllowed(orgaRole)
  public String bearbeiteTextAntwort(KeycloakAuthenticationToken token, @PathVariable Long bogennr,
      @PathVariable Long fragennr, @PathVariable Long antwortnr, Model model) {
    TextAntwort antwort = getTextAntwort(bogennr, fragennr, antwortnr);
    model.addAttribute("antwort", antwort);
    model.addAttribute("bogennr", bogennr);
    model.addAttribute("fragennr", fragennr);
    model.addAttribute("antwortnr", antwortnr);
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "dozenten/zensieren";
  }

  private TextAntwort getTextAntwort(Long bogennr, Long fragennr, Long antwortnr) {
    Fragebogen fragebogen = frageboegen.getFragebogenById(bogennr);
    TextFrage frage = (TextFrage) fragebogen.getFrage(fragennr);
    TextAntwort antwort = frage.getAntwortById(antwortnr);
    return antwort;
  }

  @PostMapping("/watch/edit/{bogennr}/{fragennr}/{antwortnr}")
  @RolesAllowed(orgaRole)
  public String speichereTextAntwort(@PathVariable Long bogennr, @PathVariable Long fragennr,
      @PathVariable Long antwortnr, String textfeld) {
    TextAntwort antwort = getTextAntwort(bogennr, fragennr, antwortnr);
    antwort.setAntworttext(textfeld);
    return "redirect:/feedback/dozenten/watch/" + bogennr;
  }

  @PostMapping("/watch/publish/{bogennr}/{fragennr}")
  @RolesAllowed(orgaRole)
  public String veroeffentlicheErgebnisseEinerFrage(@PathVariable Long bogennr,
      @PathVariable Long fragennr) {
    Frage frage = getFrage(bogennr, fragennr);
    frage.aendereOeffentlichkeitsStatus();
    return "redirect:/feedback/dozenten/watch/" + bogennr;
  }

  private Frage getFrage(Long bogennr, Long fragennr) {
    Fragebogen fragebogen = frageboegen.getFragebogenById(bogennr);
    Frage frage = fragebogen.getFrage(fragennr);
    return frage;
  }

  @GetMapping("/new")
  @RolesAllowed(orgaRole)
  public String getNeueFormularSeite(KeycloakAuthenticationToken token, Model model) {
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "dozenten/ersteller";
  }

  @PostMapping("/new")
  @RolesAllowed(orgaRole)
  public String addNeuesFormular(HttpServletRequest req) {
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
  @RolesAllowed(orgaRole)
  public String seiteUmFragenHinzuzufuegen(KeycloakAuthenticationToken token,
      @PathVariable Long bogennr, Model model) {
    model.addAttribute("typechecker", typechecker);
    model.addAttribute("neuerbogen", frageboegen.getFragebogenById(bogennr));
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "dozenten/fragenerstellen";
  }

  @PostMapping("/new/questions/delete/{bogennr}/{fragennr}")
  @RolesAllowed(orgaRole)
  public String loescheFrageAusFragebogen(@PathVariable Long bogennr, @PathVariable Long fragennr) {
    Fragebogen bogen = frageboegen.getFragebogenById(bogennr);
    bogen.loescheFrage(fragennr);
    return REDIRECT_FEEDBACK_DOZENTEN_NEW_QUESTIONS + bogennr;
  }

  @PostMapping("/new/questions/add/{bogennr}")
  @RolesAllowed(orgaRole)
  public String addTextfrage(@PathVariable Long bogennr, String fragetext, String fragetyp) {
    Frage neuefrage;
    if ("multiplechoice".equals(fragetyp)) {
      neuefrage = new MultipleChoiceFrage(fragetext);
    } else {
      neuefrage = new TextFrage(fragetext);
    }
    Fragebogen bogen = frageboegen.getFragebogenById(bogennr);
    bogen.addFrage(neuefrage);
    return REDIRECT_FEEDBACK_DOZENTEN_NEW_QUESTIONS + bogennr;
  }

  @GetMapping("/new/questions/edit/{bogennr}/{fragennr}")
  @RolesAllowed(orgaRole)
  public String seiteUmAntwortmoeglichkeitenHinzuzufuegen(Model model,
      KeycloakAuthenticationToken token, @PathVariable Long bogennr, @PathVariable Long fragennr) {
    MultipleChoiceFrage frage = getMultipleChoiceFrage(bogennr, fragennr);
    model.addAttribute("frage", frage);
    model.addAttribute("fragebogen", bogennr);
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "dozenten/multiplechoiceedit";
  }

  private MultipleChoiceFrage getMultipleChoiceFrage(Long bogennr, Long fragennr) {
    Fragebogen bogen = frageboegen.getFragebogenById(bogennr);
    MultipleChoiceFrage frage = (MultipleChoiceFrage) bogen.getFrage(fragennr);
    return frage;
  }

  @PostMapping("/new/questions/mc/add/{bogennr}/{fragennr}")
  @RolesAllowed(orgaRole)
  public String neueMultipleChoiceAntwort(@PathVariable Long bogennr, @PathVariable Long fragennr,
      String antworttext) {
    MultipleChoiceFrage frage = getMultipleChoiceFrage(bogennr, fragennr);
    frage.addChoice(new Auswahl(antworttext));
    return "redirect:/feedback/dozenten/new/questions/edit/" + bogennr + "/" + fragennr;
  }

  @PostMapping("/new/questions/mc/delete/{bogennr}/{fragennr}/{antwortnr}")
  @RolesAllowed(orgaRole)
  public String loescheMultipleChoiceAntwort(@PathVariable Long bogennr,
      @PathVariable Long fragennr, @PathVariable Long antwortnr, String antworttext) {
    MultipleChoiceFrage frage = getMultipleChoiceFrage(bogennr, fragennr);
    frage.deleteChoice(antwortnr);
    return "redirect:/feedback/dozenten/new/questions/edit/" + bogennr + "/" + fragennr;
  }

  private Account createAccountFromPrincipal(KeycloakAuthenticationToken token) {
    KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
    return new Account(principal.getName(),
        principal.getKeycloakSecurityContext().getIdToken().getEmail(), null,
        token.getAccount().getRoles());
  }
}
