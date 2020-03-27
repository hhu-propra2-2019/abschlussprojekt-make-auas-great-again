package mops.controllers;

import javax.annotation.security.RolesAllowed;
import mops.DozentService;
import mops.TypeChecker;
import mops.database.repos.DatenbankSchnittstelle;
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

@Controller
@RequestMapping("/feedback/dozenten/templates")
public class DozentTemplateController {
  private static final String REDIRECT_FEEDBACK_DOZENTEN_TEMPLATES =
      "redirect:/feedback/dozenten/templates/";
  private static final String ORGA_ROLE = "ROLE_orga";

  private final transient DozentService dozentservice;
  private final transient TypeChecker typechecker;
  private final transient DatenbankSchnittstelle db;

  public DozentTemplateController(DatenbankSchnittstelle db) {
    this.db = db;
    dozentservice = new DozentService();
    typechecker = new TypeChecker();
  }

  @GetMapping("")
  @RolesAllowed(ORGA_ROLE)
  public String getTemplatePage(Model model, KeycloakAuthenticationToken token) {
    Dozent dozent = getDozentFromToken(token);
    model.addAttribute("templates", dozent.getTemplates());
    model.addAttribute("account", createAccountFromPrincipal(token));
    return "dozenten/templates";
  }

  @PostMapping("")
  @RolesAllowed(ORGA_ROLE)
  public String neuesTemplate(String templatename, KeycloakAuthenticationToken token) {
    Dozent dozent = getDozentFromToken(token);
    Long templateid = dozentservice.createNewTemplate(dozent, templatename);
    db.saveDozent(dozent);
    return REDIRECT_FEEDBACK_DOZENTEN_TEMPLATES + templateid;
  }

  @GetMapping("/{templatenr}")
  @RolesAllowed(ORGA_ROLE)
  public String templateBearbeitung(@PathVariable Long templatenr,
      KeycloakAuthenticationToken token, Model model) {
    Dozent dozent = getDozentFromToken(token);
    model.addAttribute("template", dozent.getTemplateById(templatenr));
    model.addAttribute("typechecker", typechecker);
    model.addAttribute("account", createAccountFromPrincipal(token));
    return "dozenten/edittemplate";
  }

  @PostMapping("/{templatenr}")
  @RolesAllowed(ORGA_ROLE)
  public String neueFrage(@PathVariable Long templatenr, KeycloakAuthenticationToken token,
      String fragetyp, String fragetext) {
    Dozent dozent = getDozentFromToken(token);
    dozentservice.addFrageZuTemplate(dozent, templatenr, fragetyp, fragetext);
    db.saveDozent(dozent);
    return REDIRECT_FEEDBACK_DOZENTEN_TEMPLATES + templatenr;
  }

  @GetMapping("/{templatenr}/{fragennr}")
  @RolesAllowed(ORGA_ROLE)
  public String editMultipleChoiceQuestion(@PathVariable Long templatenr,
      @PathVariable Long fragennr, KeycloakAuthenticationToken token, Model model) {
    Dozent dozent = getDozentFromToken(token);
    model.addAttribute("account", createAccountFromPrincipal(token));
    model.addAttribute("frage", dozentservice.getMultipleChoiceFromTemplate(fragennr,
        dozent, templatenr));
    model.addAttribute("template", templatenr);
    return "dozenten/mcedit-template";
  }

  @PostMapping("/{templatenr}/{fragennr}")
  @RolesAllowed(ORGA_ROLE)
  public String newMultipleChoiceAnswer(@PathVariable Long templatenr, @PathVariable Long fragennr,
      KeycloakAuthenticationToken token, String antworttext) {
    Dozent dozent = getDozentFromToken(token);
    dozentservice.addMultipleChoiceToTemplate(dozent, templatenr, fragennr, antworttext);
    db.saveDozent(dozent);
    return REDIRECT_FEEDBACK_DOZENTEN_TEMPLATES + templatenr + "/" + fragennr;
  }

  @PostMapping("/delete/{templatenr}")
  @RolesAllowed(ORGA_ROLE)
  public String deleteTemplate(@PathVariable Long templatenr, KeycloakAuthenticationToken token) {
    Dozent dozent = getDozentFromToken(token);
    dozent.deleteTemplateById(templatenr);
    db.saveDozent(dozent);
    return "redirect:/feedback/dozenten/templates";
  }

  @PostMapping("/delete/{templatenr}/{fragennr}")
  @RolesAllowed(ORGA_ROLE)
  public String deleteFrage(@PathVariable Long templatenr, @PathVariable Long fragennr,
      KeycloakAuthenticationToken token) {
    Dozent dozent = getDozentFromToken(token);
    dozentservice.loescheFrageAusTemplate(dozent, templatenr, fragennr);
    db.saveDozent(dozent);
    return REDIRECT_FEEDBACK_DOZENTEN_TEMPLATES + templatenr;
  }

  @PostMapping("/delete/{templatenr}/{fragennr}/{auswahlnr}")
  @RolesAllowed(ORGA_ROLE)
  public String deleteAntwortmoeglichkeit(@PathVariable Long templatenr,
      @PathVariable Long fragennr, @PathVariable Long auswahlnr,
      KeycloakAuthenticationToken token) {
    Dozent dozent = getDozentFromToken(token);
    dozentservice.loescheMultipleChoiceAusTemplate(dozent, templatenr, fragennr, auswahlnr);
    db.saveDozent(dozent);
    return REDIRECT_FEEDBACK_DOZENTEN_TEMPLATES + templatenr + "/" + fragennr;
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
