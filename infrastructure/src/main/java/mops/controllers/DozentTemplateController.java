package mops.controllers;

import mops.DozentService;
import mops.FragebogenTemplate;
import mops.TypeChecker;
import mops.database.MockDozentenRepository;
import mops.fragen.Auswahl;
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

@Controller
@RequestMapping("/feedback/dozenten/templates")
public class DozentTemplateController {
  private static final String REDIRECT_FEEDBACK_DOZENTEN_TEMPLATES =
      "redirect:/feedback/dozenten/templates/";
  private final transient DozentRepository dozenten;
  private final transient DozentService dozentservice;
  private final transient TypeChecker typechecker;

  public DozentTemplateController() {
    dozenten = new MockDozentenRepository();
    dozentservice = new DozentService();
    typechecker = new TypeChecker();
  }

  @GetMapping("")
  public String getTemplatePage(Model model, KeycloakAuthenticationToken token) {
    Dozent dozent = getDozentFromToken(token);
    model.addAttribute("templates", dozent.getTemplates());
    model.addAttribute("account", createAccountFromPrincipal(token));
    return "dozenten/templates";
  }

  @PostMapping("")
  public String neuesTemplate(String templatename, KeycloakAuthenticationToken token) {
    FragebogenTemplate template = new FragebogenTemplate(templatename);
    Dozent dozent = getDozentFromToken(token);
    dozent.addTemplate(template);
    return REDIRECT_FEEDBACK_DOZENTEN_TEMPLATES + template.getId();
  }

  @GetMapping("/{templatenr}")
  public String templateBearbeitung(@PathVariable Long templatenr,
      KeycloakAuthenticationToken token, Model model) {
    Dozent dozent = getDozentFromToken(token);
    model.addAttribute("template", dozent.getTemplateById(templatenr));
    model.addAttribute("typechecker", typechecker);
    model.addAttribute("account", createAccountFromPrincipal(token));
    return "dozenten/edittemplate";
  }

  @PostMapping("/{templatenr}")
  public String neueFrage(@PathVariable Long templatenr, KeycloakAuthenticationToken token,
      String fragetyp, String fragetext) {
    Dozent dozent = getDozentFromToken(token);
    FragebogenTemplate template = dozent.getTemplateById(templatenr);
    template.addFrage(dozentservice.createNeueFrageAnhandFragetyp(fragetyp, fragetext));
    return REDIRECT_FEEDBACK_DOZENTEN_TEMPLATES + templatenr;
  }

  @GetMapping("/{templatenr}/{fragennr}")
  public String editMultipleChoiceQuestion(@PathVariable Long templatenr,
      @PathVariable Long fragennr, KeycloakAuthenticationToken token, Model model) {
    Dozent dozent = getDozentFromToken(token);
    FragebogenTemplate template = dozent.getTemplateById(templatenr);
    MultipleChoiceFrage frage = template.getMultipleChoiceFrageById(fragennr);
    model.addAttribute("account", createAccountFromPrincipal(token));
    model.addAttribute("frage", frage);
    model.addAttribute("template", templatenr);
    return "dozenten/mcedit-template";
  }

  @PostMapping("/{templatenr}/{fragennr}")
  public String newMultipleChoiceAnswer(@PathVariable Long templatenr, @PathVariable Long fragennr,
      KeycloakAuthenticationToken token, String antworttext) {
    Dozent dozent = getDozentFromToken(token);
    FragebogenTemplate template = dozent.getTemplateById(templatenr);
    MultipleChoiceFrage frage = template.getMultipleChoiceFrageById(fragennr);
    frage.addChoice(new Auswahl(antworttext));
    return REDIRECT_FEEDBACK_DOZENTEN_TEMPLATES + templatenr + "/" + fragennr;
  }

  @PostMapping("/delete/{templatenr}")
  public String deleteTemplate(@PathVariable Long templatenr, KeycloakAuthenticationToken token) {
    Dozent dozent = getDozentFromToken(token);
    dozent.deleteTemplateById(templatenr);
    return "redirect:/feedback/dozenten/templates";
  }

  @PostMapping("/delete/{templatenr}/{fragennr}")
  public String deleteFrage(@PathVariable Long templatenr, @PathVariable Long fragennr,
      KeycloakAuthenticationToken token) {
    Dozent dozent = getDozentFromToken(token);
    FragebogenTemplate template = dozent.getTemplateById(templatenr);
    template.deleteFrageById(fragennr);
    return REDIRECT_FEEDBACK_DOZENTEN_TEMPLATES + templatenr;
  }

  @PostMapping("/delete/{templatenr}/{fragennr}/{auswahlnr}")
  public String deleteAntwortmoeglichkeit(@PathVariable Long templatenr,
      @PathVariable Long fragennr, @PathVariable Long auswahlnr,
      KeycloakAuthenticationToken token) {
    Dozent dozent = getDozentFromToken(token);
    FragebogenTemplate template = dozent.getTemplateById(templatenr);
    MultipleChoiceFrage frage = template.getMultipleChoiceFrageById(fragennr);
    frage.deleteChoice(auswahlnr);
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
    return dozenten.getDozentByUsername(principal.getName());
  }
}
