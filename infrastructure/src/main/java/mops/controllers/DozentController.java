package mops.controllers;

import java.util.ArrayList;
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
import mops.Veranstaltung;
import mops.antworten.TextAntwort;
import mops.database.MockVeranstaltungsRepository;
import mops.fragen.Auswahl;
import mops.fragen.Frage;
import mops.fragen.MultipleChoiceFrage;
import mops.fragen.TextFrage;
import mops.rollen.Dozent;
import mops.security.Account;


@Controller
@RequestMapping("/feedback/dozenten")
public class DozentController {
  public static final String orgaRole = "ROLE_orga";
  public static final String account = "account";
  private static final String REDIRECT_FEEDBACK_DOZENTEN_NEW_QUESTIONS =
      "redirect:/feedback/dozenten/new/questions/";
  private final transient VeranstaltungsRepository veranstaltungen;
  private final transient TypeChecker typechecker;
  private final transient DateTimeService datetime;

  public DozentController() {
    veranstaltungen = new MockVeranstaltungsRepository();
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
    Dozent dozent = createDozentFromToken(token);
    model.addAttribute("frageboegen", holeFrageboegenVomDozent(dozent));
    model.addAttribute("typechecker", typechecker);
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "dozenten/frageboegen";
  }

  @GetMapping("/watch/{bogennr}")
  @RolesAllowed(orgaRole)
  public String getAntwortenEinesFragebogens(KeycloakAuthenticationToken token,
      @PathVariable long bogennr, Model model) {
    Dozent dozent = createDozentFromToken(token);
    Fragebogen fragebogen = veranstaltungen.getFragebogenFromDozentById(bogennr, dozent);
    model.addAttribute("fragebogen", fragebogen);
    model.addAttribute("typechecker", typechecker);
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "dozenten/ergebnisse";
  }

  @GetMapping("/watch/edit/{bogennr}/{fragennr}/{antwortnr}")
  @RolesAllowed(orgaRole)
  public String bearbeiteTextAntwort(KeycloakAuthenticationToken token, @PathVariable Long bogennr,
      @PathVariable Long fragennr, @PathVariable Long antwortnr, Model model) {
    Dozent dozent = createDozentFromToken(token);
    TextAntwort antwort = getTextAntwort(dozent, bogennr, fragennr, antwortnr);
    model.addAttribute("antwort", antwort);
    model.addAttribute("bogennr", bogennr);
    model.addAttribute("fragennr", fragennr);
    model.addAttribute("antwortnr", antwortnr);
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "dozenten/zensieren";
  }

  @PostMapping("/watch/edit/{bogennr}/{fragennr}/{antwortnr}")
  @RolesAllowed(orgaRole)
  public String speichereTextAntwort(@PathVariable Long bogennr, @PathVariable Long fragennr,
      @PathVariable Long antwortnr, String textfeld, KeycloakAuthenticationToken token) {
    Dozent dozent = createDozentFromToken(token);
    TextAntwort antwort = getTextAntwort(dozent, bogennr, fragennr, antwortnr);
    antwort.setAntworttext(textfeld);
    return "redirect:/feedback/dozenten/watch/" + bogennr;
  }

  @PostMapping("/watch/publish/{bogennr}/{fragennr}")
  @RolesAllowed(orgaRole)
  public String veroeffentlicheErgebnisseEinerFrage(@PathVariable Long bogennr,
      @PathVariable Long fragennr, KeycloakAuthenticationToken token) {
    Dozent dozent = createDozentFromToken(token);
    Frage frage = getFrage(dozent, bogennr, fragennr);
    frage.aendereOeffentlichkeitsStatus();
    return "redirect:/feedback/dozenten/watch/" + bogennr;
  }

  @GetMapping("/new")
  @RolesAllowed(orgaRole)
  public String getNeueFormularSeite(KeycloakAuthenticationToken token, Model model) {
    model.addAttribute("veranstaltungen",
        veranstaltungen.getAllFromDozent(createDozentFromToken(token)));
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "dozenten/ersteller";
  }

  @PostMapping("/new")
  @RolesAllowed(orgaRole)
  public String addNeuesFormular(HttpServletRequest req, KeycloakAuthenticationToken token) {
    Dozent dozent = createDozentFromToken(token);
    Veranstaltung veranstaltung =
        veranstaltungen.getVeranstaltungById(Long.parseLong(req.getParameter("veranstaltung")));
    Fragebogen neu = new Fragebogen(veranstaltung.getName(),
        dozent.getVorname() + " " + dozent.getNachname(),
        datetime.getLocalDateTimeFromString(req.getParameter("startdatum"),
            req.getParameter("startzeit")),
        datetime.getLocalDateTimeFromString(req.getParameter("enddatum"),
            req.getParameter("endzeit")),
        Einheit.valueOf(req.getParameter("veranstaltungstyp")));
    veranstaltung.addFragebogen(neu);
    return REDIRECT_FEEDBACK_DOZENTEN_NEW_QUESTIONS + neu.getBogennr();
  }

  @GetMapping("/new/questions/{bogennr}")
  @RolesAllowed(orgaRole)
  public String seiteUmFragenHinzuzufuegen(KeycloakAuthenticationToken token,
      @PathVariable Long bogennr, Model model) {
    Dozent dozent = createDozentFromToken(token);
    model.addAttribute("typechecker", typechecker);
    model.addAttribute("neuerbogen", veranstaltungen.getFragebogenFromDozentById(bogennr, dozent));
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "dozenten/fragenerstellen";
  }

  @PostMapping("/new/questions/delete/{bogennr}/{fragennr}")
  @RolesAllowed(orgaRole)
  public String loescheFrageAusFragebogen(@PathVariable Long bogennr, @PathVariable Long fragennr,
      KeycloakAuthenticationToken token) {
    Dozent dozent = createDozentFromToken(token);
    Fragebogen bogen = veranstaltungen.getFragebogenFromDozentById(bogennr, dozent);
    bogen.loescheFrage(fragennr);
    return REDIRECT_FEEDBACK_DOZENTEN_NEW_QUESTIONS + bogennr;
  }

  @PostMapping("/new/questions/add/{bogennr}")
  @RolesAllowed(orgaRole)
  public String addTextfrage(@PathVariable Long bogennr, String fragetext, String fragetyp,
      KeycloakAuthenticationToken token) {
    Dozent dozent = createDozentFromToken(token);
    Frage neuefrage;
    if ("multiplechoice".equals(fragetyp)) {
      neuefrage = new MultipleChoiceFrage(fragetext);
    } else {
      neuefrage = new TextFrage(fragetext);
    }
    Fragebogen bogen = veranstaltungen.getFragebogenFromDozentById(bogennr, dozent);
    bogen.addFrage(neuefrage);
    return REDIRECT_FEEDBACK_DOZENTEN_NEW_QUESTIONS + bogennr;
  }

  @GetMapping("/new/questions/edit/{bogennr}/{fragennr}")
  @RolesAllowed(orgaRole)
  public String seiteUmAntwortmoeglichkeitenHinzuzufuegen(Model model,
      KeycloakAuthenticationToken token, @PathVariable Long bogennr, @PathVariable Long fragennr) {
    Dozent dozent = createDozentFromToken(token);
    MultipleChoiceFrage frage = getMultipleChoiceFrage(dozent, bogennr, fragennr);
    model.addAttribute("frage", frage);
    model.addAttribute("fragebogen", bogennr);
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "dozenten/multiplechoiceedit";
  }

  @PostMapping("/new/questions/mc/add/{bogennr}/{fragennr}")
  @RolesAllowed(orgaRole)
  public String neueMultipleChoiceAntwort(@PathVariable Long bogennr, @PathVariable Long fragennr,
      String antworttext, KeycloakAuthenticationToken token) {
    Dozent dozent = createDozentFromToken(token);
    MultipleChoiceFrage frage = getMultipleChoiceFrage(dozent, bogennr, fragennr);
    frage.addChoice(new Auswahl(antworttext));
    return "redirect:/feedback/dozenten/new/questions/edit/" + bogennr + "/" + fragennr;
  }

  @PostMapping("/new/questions/mc/delete/{bogennr}/{fragennr}/{antwortnr}")
  @RolesAllowed(orgaRole)
  public String loescheMultipleChoiceAntwort(@PathVariable Long bogennr,
      @PathVariable Long fragennr, @PathVariable Long antwortnr, String antworttext,
      KeycloakAuthenticationToken token) {
    Dozent dozent = createDozentFromToken(token);
    MultipleChoiceFrage frage = getMultipleChoiceFrage(dozent, bogennr, fragennr);
    frage.deleteChoice(antwortnr);
    return "redirect:/feedback/dozenten/new/questions/edit/" + bogennr + "/" + fragennr;
  }

  private Account createAccountFromPrincipal(KeycloakAuthenticationToken token) {
    KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
    return new Account(principal.getName(),
        principal.getKeycloakSecurityContext().getIdToken().getEmail(), null,
        token.getAccount().getRoles());
  }

  private Dozent createDozentFromToken(KeycloakAuthenticationToken token) {
    KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
    return new Dozent(principal.getKeycloakSecurityContext().getIdToken().getId());
  }

  private TextAntwort getTextAntwort(Dozent dozent, Long bogennr, Long fragennr, Long antwortnr) {
    Fragebogen fragebogen = veranstaltungen.getFragebogenFromDozentById(bogennr, dozent);
    TextFrage frage = (TextFrage) fragebogen.getFrage(fragennr);
    TextAntwort antwort = frage.getAntwortById(antwortnr);
    return antwort;
  }

  private Frage getFrage(Dozent dozent, Long bogennr, Long fragennr) {
    Fragebogen fragebogen = veranstaltungen.getFragebogenFromDozentById(bogennr, dozent);
    Frage frage = fragebogen.getFrage(fragennr);
    return frage;
  }

  private List<Fragebogen> holeFrageboegenVomDozent(Dozent dozent) {
    List<Fragebogen> result = new ArrayList<>();
    for (Veranstaltung veranstaltung : veranstaltungen.getAllFromDozent(dozent)) {
      result.addAll(veranstaltung.getFrageboegen());
    }
    return result;
  }

  private MultipleChoiceFrage getMultipleChoiceFrage(Dozent dozent, Long bogennr, Long fragennr) {
    Fragebogen bogen = veranstaltungen.getFragebogenFromDozentById(bogennr, dozent);
    MultipleChoiceFrage frage = (MultipleChoiceFrage) bogen.getFrage(fragennr);
    return frage;
  }
}
