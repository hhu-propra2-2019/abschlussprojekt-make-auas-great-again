package mops.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import mops.Fragebogen;
import mops.SubmitService;
import mops.TypeChecker;
import mops.Veranstaltung;
import mops.database.repos.DatenbankSchnittstelle;
import mops.fragen.Frage;
import mops.rollen.Student;
import mops.security.Account;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/feedback/studenten")
public class StudentController {
  public static final String studentRole = "ROLE_studentin";
  private final transient String account = "account";
  private final transient DatenbankSchnittstelle db;
  private final transient SubmitService submitService = new SubmitService();
  private transient TypeChecker typeChecker = new TypeChecker();

  public StudentController(DatenbankSchnittstelle db) {
    this.db = db;
  }

  @GetMapping("")
  @RolesAllowed(studentRole)
  public String uebersicht(KeycloakAuthenticationToken token, Model model, String search) {
    Student student = new Student(((KeycloakPrincipal) token.getPrincipal()).getName());
    if (searchNotEmpty(search)) {
      model.addAttribute("veranstaltungen",
          db.getVeranstaltungenFromStudentContaining(student, search));
    } else {
      model.addAttribute("veranstaltungen", db.getVeranstaltungenFromStudent(student));
    }
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "studenten/veranstaltungen";
  }

  @GetMapping("/frageboegen")
  @RolesAllowed(studentRole)
  public String fragebogen(KeycloakAuthenticationToken token,
      Model model, String search, Long veranstaltungId) {
    Veranstaltung veranstaltung = db.getVeranstaltungById(veranstaltungId);
    Student student = new Student(((KeycloakPrincipal) token.getPrincipal()).getName());
    List<Fragebogen> notSubmittedFrageboegen = submitService
        .notSubmittedFrageboegen(veranstaltung.getFrageboegen(), student);
    if (searchNotEmpty(search)) {
      List<Fragebogen> searchedAndNotSubmitted = submitService
          .frageboegenContaining(notSubmittedFrageboegen, search);
      model.addAttribute("frageboegen", searchedAndNotSubmitted);
    } else {
      model.addAttribute("frageboegen", notSubmittedFrageboegen);
    }
    model.addAttribute("veranstaltung", veranstaltung);
    model.addAttribute("typeChecker", typeChecker);
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "studenten/fragebogen-uebersicht";
  }

  @GetMapping("/frageboegen/details")
  @RolesAllowed(studentRole)
  public String fragebogenDetails(KeycloakAuthenticationToken token, Model model, @RequestParam Long
      fragebogen, @RequestParam Long veranstaltung) {
    Fragebogen fragebogen1 = db.getFragebogenById(fragebogen);
    model.addAttribute("fragebogen", fragebogen1);
    model.addAttribute("typeChecker", typeChecker);
    Veranstaltung veranstaltung1 = db.getVeranstaltungById(veranstaltung);
    model.addAttribute("veranstaltung", veranstaltung1);
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "studenten/fragebogen-details";
  }

  @PostMapping("/details/submit")
  @RolesAllowed(studentRole)
  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  public String submitFeedback(KeycloakAuthenticationToken token, @RequestParam Long bogennr,
      HttpServletRequest req, Model model,
      @RequestParam Long veranstaltung) {
    Fragebogen fragebogen = db.getFragebogenById(bogennr);
    Map<Long, List<String>> antworten = new HashMap<>();
    for (Frage frage : fragebogen.getFragen()) {
      antworten.put(frage.getId(), List.of(req.getParameterValues("answer-" + frage.getId())));
    }
    submitService.saveAntworten(fragebogen, antworten);
    Student student = new Student(((KeycloakPrincipal) token.getPrincipal()).getName());
    submitService.addStudentAsSubmitted(fragebogen, student);
    db.saveFragebogen(fragebogen);
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "redirect:/feedback/studenten";
  }


  private boolean searchNotEmpty(String search) {
    return !"".equals(search) && search != null;
  }

  private Account createAccountFromPrincipal(KeycloakAuthenticationToken token) {
    KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
    return new Account(
        principal.getName(),
        principal.getKeycloakSecurityContext().getIdToken().getEmail(),
        null,
        token.getAccount().getRoles());
  }
}
