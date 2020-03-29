package mops.controllers;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import mops.DateTimeService;
import mops.DozentService;
import mops.Fragebogen;
import mops.TypeChecker;
import mops.Veranstaltung;
import mops.database.DatenbankSchnittstelle;
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

  private final transient DatenbankSchnittstelle db;
  private final transient TypeChecker typechecker;
  private final transient DateTimeService datetime;
  private final transient DozentService dozentservice;

  public DozentErstellerController(DatenbankSchnittstelle db) {
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
    Veranstaltung veranstaltung = db.getVeranstaltungById(veranstaltungid);
    dozentservice.fuegeFragebogenZuVeranstaltungHinzu(veranstaltung, dozent);
    db.saveVeranstaltung(veranstaltung);
    ra.addAttribute(VERANSTALTUNG_ID, veranstaltungid);
    return "redirect:/feedback/dozenten/event/" + veranstaltungid;
  }

  @PostMapping("/recycle/{bogennr}")
  @RolesAllowed(orgaRole)
  public String fragebogenWiederverwenden(Long veranstaltungid, RedirectAttributes ra,
      @PathVariable Long bogennr) {
    Veranstaltung veranstaltung = db.getVeranstaltungById(veranstaltungid);
    Fragebogen fragebogen = db.getFragebogenById(bogennr);
    dozentservice.kloneFragebogen(fragebogen, veranstaltung);
    ra.addAttribute(VERANSTALTUNG_ID, veranstaltungid);
    db.saveVeranstaltung(veranstaltung);
    return "redirect:/feedback/dozenten/event/" + veranstaltungid;
  }

  @SuppressWarnings({"PMD.DataflowAnomalyAnalysis"})
  @PostMapping("/questions/template/{bogennr}")
  public String fuegeTemplateHinzu(@PathVariable Long bogennr, Long bogenvorlage,
      KeycloakAuthenticationToken token, Long veranstaltungid, RedirectAttributes ra) {
    Dozent dozent = getDozentFromToken(token);
    Fragebogen fragebogen = db.getFragebogenById(bogennr);
    dozentservice.addFragenAusTemplateZuFragebogen(fragebogen, dozent, bogenvorlage);
    db.saveFragebogen(fragebogen);
    ra.addAttribute(VERANSTALTUNG_ID, veranstaltungid);
    return REDIRECT_FEEDBACK_DOZENTEN_NEW_QUESTIONS + bogennr;
  }

  @PostMapping("/meta/{bogennr}")
  @RolesAllowed(orgaRole)
  public String changeMetadaten(KeycloakAuthenticationToken token, @PathVariable Long bogennr,
      HttpServletRequest req, RedirectAttributes ra, Long veranstaltungid) {
    Fragebogen fragebogen = db.getFragebogenById(bogennr);
    dozentservice.updateFragebogenMetadaten(fragebogen, req.getParameter("veranstaltungsname"),
        req.getParameter("veranstaltungstyp"),
        req.getParameter("startdatum"), req.getParameter("startzeit"),
        req.getParameter("enddatum"), req.getParameter("endzeit"));
    db.saveFragebogen(fragebogen);
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
    model.addAttribute("neuerbogen", db.getFragebogenById(bogennr));
    model.addAttribute("veranstaltung", veranstaltungid);
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "dozenten/fragenerstellen";
  }

  @PostMapping("/questions/delete/{bogennr}/{fragennr}")
  @RolesAllowed(orgaRole)
  public String loescheFrageAusFragebogen(@PathVariable Long bogennr, @PathVariable Long fragennr,
      KeycloakAuthenticationToken token, RedirectAttributes ra, Long veranstaltungid) {
    Fragebogen fragebogen = db.getFragebogenById(bogennr);
    dozentservice.loescheFrageAusFragebogen(fragebogen, fragennr);
    db.saveFragebogen(fragebogen);
    ra.addAttribute(VERANSTALTUNG_ID, veranstaltungid);
    return REDIRECT_FEEDBACK_DOZENTEN_NEW_QUESTIONS + bogennr;
  }

  @PostMapping("/questions/add/{bogennr}")
  @RolesAllowed(orgaRole)
  public String addTextfrage(@PathVariable Long bogennr, String fragetext, String fragetyp,
      KeycloakAuthenticationToken token, RedirectAttributes ra, Long veranstaltungid) {
    Fragebogen fragebogen = db.getFragebogenById(bogennr);
    dozentservice.addNeueFrageZuFragebogen(fragebogen, fragetext, fragetyp);
    db.saveFragebogen(fragebogen);
    ra.addAttribute(VERANSTALTUNG_ID, veranstaltungid);
    return REDIRECT_FEEDBACK_DOZENTEN_NEW_QUESTIONS + bogennr;
  }

  @GetMapping("/questions/edit/{bogennr}/{fragennr}")
  @RolesAllowed(orgaRole)
  public String seiteUmAntwortmoeglichkeitenHinzuzufuegen(Model model,
      KeycloakAuthenticationToken token, @PathVariable Long bogennr, @PathVariable Long fragennr,
      Long veranstaltungid) {
    Fragebogen fragebogen = db.getFragebogenById(bogennr);
    model.addAttribute("frage", dozentservice.getMultipleChoiceFrage(fragennr, fragebogen));
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
    Fragebogen fragebogen = db.getFragebogenById(bogennr);
    dozentservice.addMultipleChoiceMoeglichkeit(fragebogen, fragennr, antworttext);
    db.saveFragebogen(fragebogen);
    ra.addAttribute(VERANSTALTUNG_ID, veranstaltungid);
    ra.addAttribute("fragebogenid", fragebogenid);
    return "redirect:/feedback/dozenten/new/questions/edit/" + bogennr + "/" + fragennr;
  }

  @PostMapping("/questions/mc/delete/{bogennr}/{fragennr}/{antwortnr}")
  @RolesAllowed(orgaRole)
  public String loescheMultipleChoiceAntwort(@PathVariable Long bogennr,
      @PathVariable Long fragennr, @PathVariable Long antwortnr, KeycloakAuthenticationToken token,
      Long veranstaltungid, RedirectAttributes ra, Long fragebogenid) {
    Fragebogen fragebogen = db.getFragebogenById(bogennr);
    dozentservice.loescheMultipleChoiceMoeglichkeit(fragebogen, fragennr, antwortnr);
    db.saveFragebogen(fragebogen);
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
    return db.getDozentByUsername(principal.getName());
  }
}
