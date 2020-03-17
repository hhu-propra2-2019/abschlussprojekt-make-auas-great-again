package mops.controllers;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
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
import mops.antworten.TextAntwort;
import mops.database.MockFragebogenRepository;
import mops.fragen.Auswahl;
import mops.fragen.Frage;
import mops.fragen.MultipleChoiceFrage;
import mops.fragen.TextFrage;
import mops.security.Account;


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
    Fragebogen fragebogen = frageboegen.getFragebogenById(bogennr);
    TextFrage frage = (TextFrage) fragebogen.getFrage(fragennr);
    TextAntwort antwort = frage.getAntwortById(antwortnr);
    model.addAttribute("antwort", antwort);
    model.addAttribute("bogennr", bogennr);
    model.addAttribute("fragennr", fragennr);
    model.addAttribute("antwortnr", antwortnr);
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "dozenten/zensieren";
  }

  @PostMapping("/watch/edit/{bogennr}/{fragennr}/{antwortnr}")
  @RolesAllowed(orgaRole)
  public String speichereTextAntwort(KeycloakAuthenticationToken token, @PathVariable Long bogennr,
      @PathVariable Long fragennr, @PathVariable Long antwortnr, Model model, String textfeld) {
    Fragebogen fragebogen = frageboegen.getFragebogenById(bogennr);
    TextFrage frage = (TextFrage) fragebogen.getFrage(fragennr);
    TextAntwort antwort = frage.getAntwortById(antwortnr);
    antwort.setAntworttext(textfeld);
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "redirect:/feedback/dozenten/watch/" + bogennr;
  }

  @GetMapping("/new")
  @RolesAllowed(orgaRole)
  public String getNeueFormularSeite(KeycloakAuthenticationToken token, Model model) {
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "dozenten/ersteller";
  }

  @PostMapping("/new")
  @RolesAllowed(orgaRole)
  public String addNeuesFormular(KeycloakAuthenticationToken token, HttpServletRequest req,
      Model model) {
    Fragebogen neu = new Fragebogen(req.getParameter("veranstaltung"),
        req.getParameter("dozentname"),
        datetime.getLocalDateTimeFromString(req.getParameter("startdatum"),
            req.getParameter("startzeit")),
        datetime.getLocalDateTimeFromString(req.getParameter("enddatum"),
            req.getParameter("endzeit")),
        Einheit.valueOf(req.getParameter("veranstaltungstyp")));
    frageboegen.newFragebogen(neu);
    model.addAttribute(account, createAccountFromPrincipal(token));
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
  public String loescheFrageAusFragebogen(Model model, KeycloakAuthenticationToken token,
      @PathVariable Long bogennr, @PathVariable Long fragennr) {
    Fragebogen bogen = frageboegen.getFragebogenById(bogennr);
    bogen.loescheFrage(fragennr);
    model.addAttribute(account, createAccountFromPrincipal(token));
    return REDIRECT_FEEDBACK_DOZENTEN_NEW_QUESTIONS + bogennr;
  }

  @PostMapping("/new/questions/add/{bogennr}")
  @RolesAllowed(orgaRole)
  public String addTextfrage(Model model, KeycloakAuthenticationToken token,
      @PathVariable Long bogennr, String fragetext, String fragetyp) {
    Frage neuefrage;
    if ("multiplechoice".equals(fragetyp)) {
      neuefrage = new MultipleChoiceFrage(fragetext);
    } else {
      neuefrage = new TextFrage(fragetext);
    }
    Fragebogen bogen = frageboegen.getFragebogenById(bogennr);
    bogen.addFrage(neuefrage);
    model.addAttribute(account, createAccountFromPrincipal(token));
    return REDIRECT_FEEDBACK_DOZENTEN_NEW_QUESTIONS + bogennr;
  }

  @GetMapping("/new/questions/edit/{bogennr}/{fragennr}")
  @RolesAllowed(orgaRole)
  public String seiteUmAntwortmoeglichkeitenHinzuzufuegen(Model model,
      KeycloakAuthenticationToken token, @PathVariable Long bogennr, @PathVariable Long fragennr) {
    Fragebogen bogen = frageboegen.getFragebogenById(bogennr);
    MultipleChoiceFrage frage = (MultipleChoiceFrage) bogen.getFrage(fragennr);
    model.addAttribute("frage", frage);
    model.addAttribute("fragebogen", bogennr);
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "dozenten/multiplechoiceedit";
  }

  @PostMapping("/new/questions/mc/add/{bogennr}/{fragennr}")
  @RolesAllowed(orgaRole)
  public String neueMultipleChoiceAntwort(@PathVariable Long bogennr, @PathVariable Long fragennr,
      String antworttext) {
    Fragebogen bogen = frageboegen.getFragebogenById(bogennr);
    MultipleChoiceFrage frage = (MultipleChoiceFrage) bogen.getFrage(fragennr);
    frage.addChoice(new Auswahl(antworttext));
    return "redirect:/feedback/dozenten/new/questions/edit/" + bogennr + "/" + fragennr;
  }

  @PostMapping("/new/questions/mc/delete/{bogennr}/{fragennr}/{antwortnr}")
  @RolesAllowed(orgaRole)
  public String loescheMultipleChoiceAntwort(@PathVariable Long bogennr,
      @PathVariable Long fragennr, @PathVariable Long antwortnr, String antworttext) {
    Fragebogen bogen = frageboegen.getFragebogenById(bogennr);
    MultipleChoiceFrage frage = (MultipleChoiceFrage) bogen.getFrage(fragennr);
    frage.deleteChoice(antwortnr);
    return "redirect:/feedback/dozenten/new/questions/edit/" + bogennr + "/" + fragennr;
  }

  @GetMapping("/kontakt")
  @RolesAllowed(orgaRole)
  public String tickets(Model model, KeycloakAuthenticationToken token) {
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "dozenten/tickets";
  }

  private Account createAccountFromPrincipal(KeycloakAuthenticationToken token) {
    KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
    return new Account(principal.getName(),
        principal.getKeycloakSecurityContext().getIdToken().getEmail(), null,
        token.getAccount().getRoles());
  }
}
