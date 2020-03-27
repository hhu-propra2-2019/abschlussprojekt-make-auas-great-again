package mops.controllers;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import mops.DateTimeService;
import mops.DozentService;
import mops.Einheit;
import mops.Fragebogen;
import mops.FragebogenTemplate;
import mops.TypeChecker;
import mops.Veranstaltung;
import mops.fragen.Auswahl;
import mops.fragen.Frage;
import mops.fragen.MultipleChoiceFrage;
import mops.rollen.Dozent;
import mops.security.Account;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/feedback/dozenten/new")
public class DozentErstellerController {
  private static final String VERANSTALTUNG_ID = "veranstaltungid";
  private static final String account = "account";
  private static final String orgaRole = "ROLE_orga";
  private static final String REDIRECT_FEEDBACK_DOZENTEN_NEW_QUESTIONS =
      "redirect:/feedback/dozenten/new/questions/";

  private final transient VeranstaltungsRepository veranstaltungen;
  private final transient TypeChecker typechecker;
  private final transient DateTimeService datetime;
  private final transient DozentService dozentservice;

  public DozentErstellerController(mops.database.VeranstaltungsRepository veranstaltungen) {
    this.veranstaltungen = veranstaltungen;
    typechecker = new TypeChecker();
    datetime = new DateTimeService();
    dozentservice = new DozentService();
  }

  @PostMapping("")
  @RolesAllowed(orgaRole)
  public String addNeuesFormular(KeycloakAuthenticationToken token, Long veranstaltungid,
                                 RedirectAttributes ra) {
    Veranstaltung veranstaltung = veranstaltungen.getVeranstaltungById(veranstaltungid);
    Fragebogen neu = new Fragebogen(veranstaltung.getName());
    veranstaltung.addFragebogen(neu);
    ra.addAttribute(VERANSTALTUNG_ID, veranstaltungid);
    return REDIRECT_FEEDBACK_DOZENTEN_NEW_QUESTIONS + neu.getBogennr();
  }

  @PostMapping("/recycle/{bogennr}")
  @RolesAllowed(orgaRole)
  public String fragebogenWiederverwenden(Long veranstaltungid, RedirectAttributes ra,
                                          @PathVariable Long bogennr) {
    Fragebogen alt = veranstaltungen.getFragebogenById(bogennr);
    Fragebogen neu = new Fragebogen(alt.getName(),
        dozentservice.getFragenlisteOhneAntworten(alt.getFragen()), alt.getType());
    Veranstaltung veranstaltung = veranstaltungen.getVeranstaltungById(veranstaltungid);
    veranstaltung.addFragebogen(neu);
    ra.addAttribute(VERANSTALTUNG_ID, veranstaltungid);
    return REDIRECT_FEEDBACK_DOZENTEN_NEW_QUESTIONS + neu.getBogennr();
  }

  @SuppressWarnings( {"PMD.DataflowAnomalyAnalysis"})
  @PostMapping("/questions/template/{bogennr}")
  public String fuegeTemplateHinzu(@PathVariable Long bogennr, Long bogenvorlage,
                                   KeycloakAuthenticationToken token, Long veranstaltungid,
                                   RedirectAttributes ra) {
    Dozent dozent = getDozentFromToken(token);
    FragebogenTemplate template = dozent.getTemplateById(bogenvorlage);
    Fragebogen fragebogen = veranstaltungen.getFragebogenById(bogennr);
    List<Frage> fragen = dozentservice.getFragenlisteOhneAntworten(template.getFragen());
    fragen.stream().forEach(x -> fragebogen.addFrage(x));
    ra.addAttribute(VERANSTALTUNG_ID, veranstaltungid);
    return REDIRECT_FEEDBACK_DOZENTEN_NEW_QUESTIONS + bogennr;
  }

  @PostMapping("/meta/{bogennr}")
  @RolesAllowed(orgaRole)
  public String changeMetadaten(KeycloakAuthenticationToken token, @PathVariable Long bogennr,
                                HttpServletRequest req, RedirectAttributes ra, Long veranstaltungid) {
    Fragebogen fragebogen = veranstaltungen.getFragebogenById(bogennr);
    fragebogen.setName(req.getParameter("veranstaltungsname"));
    fragebogen.setType(Einheit.valueOf(req.getParameter("veranstaltungstyp")));
    fragebogen.setStartdatum(datetime.getLocalDateTimeFromString(req.getParameter("startdatum"),
        req.getParameter("startzeit")));
    fragebogen.setEnddatum(datetime.getLocalDateTimeFromString(req.getParameter("enddatum"),
        req.getParameter("endzeit")));
    ra.addAttribute(VERANSTALTUNG_ID, veranstaltungid);
    return REDIRECT_FEEDBACK_DOZENTEN_NEW_QUESTIONS + bogennr;
  }

  @GetMapping("/questions/{bogennr}")
  @RolesAllowed(orgaRole)
  public String seiteUmFragenHinzuzufuegen(KeycloakAuthenticationToken token,
                                           @PathVariable Long bogennr, Model model, Long veranstaltungid) {
    Dozent dozent = getDozentFromToken(token);
    model.addAttribute("boegenvorlagen", dozent.getTemplates());
    model.addAttribute("typechecker", typechecker);
    model.addAttribute("datetime", datetime);
    model.addAttribute("neuerbogen", veranstaltungen.getFragebogenById(bogennr));
    model.addAttribute("veranstaltung", veranstaltungid);
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "dozenten/fragenerstellen";
  }

  @PostMapping("/questions/delete/{bogennr}/{fragennr}")
  @RolesAllowed(orgaRole)
  public String loescheFrageAusFragebogen(@PathVariable Long bogennr, @PathVariable Long fragennr,
                                          KeycloakAuthenticationToken token, RedirectAttributes ra,
                                          Long veranstaltungid) {
    Fragebogen fragebogen = veranstaltungen.getFragebogenById(bogennr);
    fragebogen.loescheFrageById(fragennr);
    veranstaltungen.saveToVeranstaltung(fragebogen, veranstaltungid);
    ra.addAttribute(VERANSTALTUNG_ID, veranstaltungid);
    return REDIRECT_FEEDBACK_DOZENTEN_NEW_QUESTIONS + bogennr;
  }

  @PostMapping("/questions/add/{bogennr}")
  @RolesAllowed(orgaRole)
  public String addTextfrage(@PathVariable Long bogennr, String fragetext, String fragetyp,
                             KeycloakAuthenticationToken token, RedirectAttributes ra,
                             Long veranstaltungid) {
    Fragebogen bogen = veranstaltungen.getFragebogenById(bogennr);
    bogen.addFrage(dozentservice.createNeueFrageAnhandFragetyp(fragetyp, fragetext));
    ra.addAttribute(VERANSTALTUNG_ID, veranstaltungid);
    return REDIRECT_FEEDBACK_DOZENTEN_NEW_QUESTIONS + bogennr;
  }

  @GetMapping("/questions/edit/{bogennr}/{fragennr}")
  @RolesAllowed(orgaRole)
  public String seiteUmAntwortmoeglichkeitenHinzuzufuegen(Model model,
                                                          KeycloakAuthenticationToken token,
                                                          @PathVariable Long bogennr,
                                                          @PathVariable Long fragennr,
                                                          Long veranstaltungid) {
    MultipleChoiceFrage frage = dozentservice.getMultipleChoiceFrage(fragennr,
        veranstaltungen.getFragebogenById(bogennr));
    model.addAttribute("frage", frage);
    model.addAttribute("fragebogen", bogennr);
    model.addAttribute("veranstaltung", veranstaltungid);
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "dozenten/multiplechoiceedit";
  }

  @PostMapping("/questions/mc/add/{bogennr}/{fragennr}")
  @RolesAllowed(orgaRole)
  public String neueMultipleChoiceAntwort(@PathVariable Long bogennr, @PathVariable Long fragennr,
                                          String antworttext, KeycloakAuthenticationToken token,
                                          Long veranstaltungid,
                                          RedirectAttributes ra, Long fragebogenid) {
    // TODO Refactor
    Fragebogen fragebogen = veranstaltungen.getFragebogenById(fragebogenid);
    dozentservice.getMultipleChoiceFrage(fragennr, fragebogen).addChoice(new Auswahl(antworttext));
    veranstaltungen.saveToVeranstaltung(fragebogen, veranstaltungid);
    ra.addAttribute(VERANSTALTUNG_ID, veranstaltungid);
    ra.addAttribute("fragebogenid", fragebogenid);
    return "redirect:/feedback/dozenten/new/questions/edit/" + bogennr + "/" + fragennr;
  }

  @PostMapping("/questions/mc/delete/{bogennr}/{fragennr}/{antwortnr}")
  @RolesAllowed(orgaRole)
  public String loescheMultipleChoiceAntwort(@PathVariable Long bogennr,
                                             @PathVariable Long fragennr, @PathVariable Long antwortnr,
                                             KeycloakAuthenticationToken token,
                                             Long veranstaltungid, RedirectAttributes ra,
                                             Long fragebogenid) {
    dozentservice.getMultipleChoiceFrage(fragennr,
        veranstaltungen.getFragebogenById(bogennr)).deleteChoice(antwortnr);
    ra.addAttribute(VERANSTALTUNG_ID, veranstaltungid);
    ra.addAttribute("fragebogenid", fragebogenid);
    return "redirect:/feedback/dozenten/new/questions/edit/" + bogennr + "/" + fragennr;
  }

  private Account createAccountFromPrincipal(KeycloakAuthenticationToken token) {
    KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
    return new Account(principal.getName(),
        principal.getKeycloakSecurityContext().getIdToken().getEmail(), null,
        token.getAccount().getRoles());
  }

  private Dozent getDozentFromToken(KeycloakAuthenticationToken token) {
    KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
    return veranstaltungen.getDozentByUsername(principal.getName());
  }
}
