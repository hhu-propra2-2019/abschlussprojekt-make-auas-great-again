package mops.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import mops.Fragebogen;
import mops.SubmitService;
import mops.TypeChecker;
import mops.Veranstaltung;
import mops.database.MockFragebogenRepository;
import mops.database.MockVeranstaltungsRepository;
import mops.fragen.Frage;
import mops.rollen.Student;
import mops.security.Account;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/feedback/studenten")
public class StudentController {
  public static final String studentRole = "ROLE_studentin";
  private static final String emptySearchString = "";
  private final transient String account = "account";
  private final transient VeranstaltungsRepository veranstaltungen;
  private final transient SubmitService submitService;
  private transient FragebogenRepository frageboegen;
  private transient TypeChecker typeChecker;

  public StudentController(MockFragebogenRepository frageboegen,
                           MockVeranstaltungsRepository veranstaltungen) {
    this.veranstaltungen = veranstaltungen;
    this.submitService = new SubmitService();
    this.frageboegen = frageboegen;
    this.typeChecker = new TypeChecker();
  }

  @GetMapping("")
  @RolesAllowed(studentRole)
  public String uebersicht(KeycloakAuthenticationToken token, Model model, String search) {
    KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
    Student student = new Student(UUID.fromString(principal.getName()));
    if (searchNotEmpty(search)) {
      model.addAttribute("veranstaltungen",
          veranstaltungen.getAllFromStudentContaining(student, search));
    } else {
      model.addAttribute("veranstaltungen",
          veranstaltungen.getAllFromStudent(student));
    }
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "studenten/veranstaltungen";
  }

  @GetMapping("/frageboegen")
  @RolesAllowed(studentRole)
  public String fragebogen(KeycloakAuthenticationToken token,
                           Model model, String search, Long veranstaltungId) {
    Veranstaltung veranstaltung = veranstaltungen.getVeranstaltungById(veranstaltungId);
    if (searchNotEmpty(search)) {
      model.addAttribute("frageboegen", veranstaltung.getFrageboegenContaining(search));
    } else {
      model.addAttribute("frageboegen", veranstaltung.getFrageboegen());
    }
    model.addAttribute("typeChecker", typeChecker);
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "studenten/fragebogen_uebersicht";
  }

  @GetMapping("/frageboegen/details")
  @RolesAllowed(studentRole)
  public String fragebogenDetails(KeycloakAuthenticationToken token, Model model, @RequestParam Long
      id) {
    model.addAttribute("fragebogen", frageboegen.getFragebogenById(id));
    model.addAttribute("typeChecker", typeChecker);
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "studenten/fragebogen_details";
  }

  @PostMapping("/details/submit/{bogennr}")
  @RolesAllowed(studentRole)
  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  public String submitFeedback(KeycloakAuthenticationToken token, @PathVariable long bogennr,
                               HttpServletRequest req, Model model) {
    Fragebogen fragebogen = frageboegen.getFragebogenById(bogennr);
    Map<Long, String> antworten = new HashMap<>();
    for (Frage frage : fragebogen.getFragen()) {
      antworten.put(frage.getId(), req.getParameter("answer-" + frage.getId()));
    }
    submitService.saveAntworten(fragebogen, antworten);
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "redirect:/feedback/studenten";
  }

  @GetMapping("/ergebnis")
  @RolesAllowed(studentRole)
  public String boegenUebersicht(KeycloakAuthenticationToken token, Model model) {
    model.addAttribute("frageboegen", frageboegen.getAll());
    model.addAttribute("typeChecker", typeChecker);
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "/studenten/ergebnis";
  }

  @GetMapping("/ergebnisUebersicht")
  @RolesAllowed(studentRole)
  public String ergebnisUebersicht(KeycloakAuthenticationToken token,
                                   @RequestParam long id, Model model) {
    Fragebogen fragebogen = frageboegen.getFragebogenById(id);
    model.addAttribute("fragebogen", fragebogen);
    model.addAttribute("textFragen", fragebogen.getTextfragen());
    model.addAttribute("multChoiceFragen", fragebogen.getMultipleChoiceFragen());
    model.addAttribute(account, createAccountFromPrincipal(token));
    return "/studenten/ergebnisUebersicht";
  }

  private boolean searchNotEmpty(String search) {
    return !emptySearchString.equals(search) && search != null;
  }

  private Account createAccountFromPrincipal(KeycloakAuthenticationToken token) {
    KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
    return new Account(
        principal.getKeycloakSecurityContext().getIdToken().getName(),
        principal.getKeycloakSecurityContext().getIdToken().getEmail(),
        null,
        token.getAccount().getRoles());
  }
}
