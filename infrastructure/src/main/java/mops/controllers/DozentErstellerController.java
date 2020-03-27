package mops.controllers;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import mops.DateTimeService;
import mops.DozentService;
import mops.Fragebogen;
import mops.TypeChecker;
import mops.Veranstaltung;
import mops.database.DatabaseService;
import mops.rollen.Dozent;
import mops.security.Account;

@Controller
@RequestMapping("/feedback/dozenten/new")
public class DozentErstellerController {
  private static final String VERANSTALTUNG_ID = "veranstaltungid";
  private static final String account = "account";
  private static final String orgaRole = "ROLE_orga";
  private static final String REDIRECT_FEEDBACK_DOZENTEN_NEW_QUESTIONS =
      "redirect:/feedback/dozenten/new/questions/";

  private final transient DatabaseService db;
  private final transient TypeChecker typechecker;
  private final transient DateTimeService datetime;
  private final transient DozentService dozentservice;

  public DozentErstellerController(DatabaseService db) {
    this.db = db;
    typechecker = new TypeChecker();
    datetime = new DateTimeService();
    dozentservice = new DozentService();
  }

  @PostMapping("")
  @RolesAllowed(orgaRole)
  public String addNeuesFormular(KeycloakAuthenticationToken token, Long veranstaltungid,
      RedirectAttributes ra) {
    Dozent dozent = getDozentFromToken(token);
    Veranstaltung veranstaltung = veranstaltungen.getVeranstaltungById(veranstaltungid);
    Long bogennr =
        dozentservice.fuegeFragebogenZuVeranstaltungHinzu(veranstaltung, dozent);
    veranstaltungen.save(veranstaltung);
    ra.addAttribute(VERANSTALTUNG_ID, veranstaltungid);
    return REDIRECT_FEEDBACK_DOZENTEN_NEW_QUESTIONS + bogennr;
  }

  @PostMapping("/recycle/{bogennr}")
  @RolesAllowed(orgaRole)
  public String fragebogenWiederverwenden(Long veranstaltungid, RedirectAttributes ra,
      @PathVariable Long bogennr) {
    Veranstaltung veranstaltung = veranstaltungen.getVeranstaltungById(veranstaltungid);
    Long neuebogennr = dozentservice.kloneFragebogen(
        veranstaltungen.getFragebogenByIdFromVeranstaltung(bogennr, veranstaltungid),
        veranstaltung);
    ra.addAttribute(VERANSTALTUNG_ID, veranstaltungid);
    return REDIRECT_FEEDBACK_DOZENTEN_NEW_QUESTIONS + neuebogennr;
  }

  @SuppressWarnings( {"PMD.DataflowAnomalyAnalysis"})
  @PostMapping("/questions/template/{bogennr}")
  public String fuegeTemplateHinzu(@PathVariable Long bogennr, Long bogenvorlage,
      KeycloakAuthenticationToken token, Long veranstaltungid, RedirectAttributes ra) {
    Dozent dozent = getDozentFromToken(token);
    Fragebogen fragebogen = veranstaltungen.getFragebogenFromDozentById(bogennr, dozent);
    dozentservice.addFragenAusTemplateZuFragebogen(fragebogen, dozent, bogenvorlage);
    veranstaltungen.saveToVeranstaltung(fragebogen, veranstaltungid);
    ra.addAttribute(VERANSTALTUNG_ID, veranstaltungid);
    return REDIRECT_FEEDBACK_DOZENTEN_NEW_QUESTIONS + bogennr;
  }

  @PostMapping("/meta/{bogennr}")
  @RolesAllowed(orgaRole)
  public String changeMetadaten(KeycloakAuthenticationToken token, @PathVariable Long bogennr,
      HttpServletRequest req, RedirectAttributes ra, Long veranstaltungid) {
    Fragebogen fragebogen = veranstaltungen.getFragebogenFromDozentById(bogennr, getDozentFromToken(token));
    dozentservice.updateFragebogenMetadaten(fragebogen, req.getParameter("veranstaltungsname"),
        req.getParameter("veranstaltungstyp"),
        req.getParameter("startdatum"), req.getParameter("startzeit"),
        req.getParameter("enddatum"), req.getParameter("endzeit"));
    veranstaltungen.saveToVeranstaltung(fragebogen, veranstaltungid);
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
    model.addAttribute("neuerbogen", veranstaltungen.getFragebogenFromDozentById(bogennr, dozent));
    model.addAttribute("veranstaltung", veranstaltungid);
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "dozenten/fragenerstellen";
  }

  @PostMapping("/questions/delete/{bogennr}/{fragennr}")
  @RolesAllowed(orgaRole)
  public String loescheFrageAusFragebogen(@PathVariable Long bogennr, @PathVariable Long fragennr,
      KeycloakAuthenticationToken token, RedirectAttributes ra, Long veranstaltungid) {
    Dozent dozent = getDozentFromToken(token);
    Fragebogen fragebogen = veranstaltungen.getFragebogenFromDozentById(bogennr, dozent);
    dozentservice.loescheFrageAusFragebogen(fragebogen, fragennr);
    veranstaltungen.saveToVeranstaltung(fragebogen, veranstaltungid);
    ra.addAttribute(VERANSTALTUNG_ID, veranstaltungid);
    return REDIRECT_FEEDBACK_DOZENTEN_NEW_QUESTIONS + bogennr;
  }

  @PostMapping("/questions/add/{bogennr}")
  @RolesAllowed(orgaRole)
  public String addTextfrage(@PathVariable Long bogennr, String fragetext, String fragetyp,
      KeycloakAuthenticationToken token, RedirectAttributes ra, Long veranstaltungid) {
    Dozent dozent = getDozentFromToken(token);
    Fragebogen fragebogen = veranstaltungen.getFragebogenFromDozentById(bogennr, dozent);
    dozentservice.addNeueFrageZuFragebogen(fragebogen, fragetext, fragetyp);
    veranstaltungen.saveToVeranstaltung(fragebogen, veranstaltungid);
    ra.addAttribute(VERANSTALTUNG_ID, veranstaltungid);
    return REDIRECT_FEEDBACK_DOZENTEN_NEW_QUESTIONS + bogennr;
  }

  @GetMapping("/questions/edit/{bogennr}/{fragennr}")
  @RolesAllowed(orgaRole)
  public String seiteUmAntwortmoeglichkeitenHinzuzufuegen(Model model,
      KeycloakAuthenticationToken token, @PathVariable Long bogennr, @PathVariable Long fragennr,
      Long veranstaltungid) {
    Dozent dozent = getDozentFromToken(token);
    model.addAttribute("frage", dozentservice.getMultipleChoiceFrage(fragennr,
        veranstaltungen.getFragebogenFromDozentById(bogennr, dozent)));
    model.addAttribute("fragebogen", bogennr);
    model.addAttribute("veranstaltung", veranstaltungid);
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "dozenten/multiplechoiceedit";
  }

  @PostMapping("/questions/mc/add/{bogennr}/{fragennr}")
  @RolesAllowed(orgaRole)
  public String neueMultipleChoiceAntwort(@PathVariable Long bogennr, @PathVariable Long fragennr,
      String antworttext, KeycloakAuthenticationToken token, Long veranstaltungid,
      RedirectAttributes ra, Long fragebogenid) {
    Fragebogen fragebogen =
        veranstaltungen.getFragebogenByIdFromVeranstaltung(fragebogenid, veranstaltungid);
    dozentservice.addMultipleChoiceMoeglichkeit(fragebogen, fragennr, antworttext);
    veranstaltungen.saveToVeranstaltung(fragebogen, veranstaltungid);
    ra.addAttribute(VERANSTALTUNG_ID, veranstaltungid);
    ra.addAttribute("fragebogenid", fragebogenid);
    return "redirect:/feedback/dozenten/new/questions/edit/" + bogennr + "/" + fragennr;
  }

  @PostMapping("/questions/mc/delete/{bogennr}/{fragennr}/{antwortnr}")
  @RolesAllowed(orgaRole)
  public String loescheMultipleChoiceAntwort(@PathVariable Long bogennr,
      @PathVariable Long fragennr, @PathVariable Long antwortnr, KeycloakAuthenticationToken token,
      Long veranstaltungid, RedirectAttributes ra, Long fragebogenid) {
    Dozent dozent = getDozentFromToken(token);
    Fragebogen fragebogen = veranstaltungen.getFragebogenFromDozentById(bogennr, dozent);
    dozentservice.loescheMultipleChoiceMoeglichkeit(fragebogen, fragennr, antwortnr);
    veranstaltungen.saveToVeranstaltung(fragebogen, veranstaltungid);
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
