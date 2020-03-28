package mops.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import com.c4_soft.springaddons.test.security.context.support.WithIDToken;
import com.c4_soft.springaddons.test.security.context.support.WithMockKeycloackAuth;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

@SpringBootTest
@AutoConfigureMockMvc
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
class StudentControllerTest {
  private final transient String usermail = "user@mail.de";
  private final transient String userrole = "studentin";
  @Autowired
  private transient MockMvc mvc;

  @Test
  @DisplayName("Student sollte auf die student_uebersicht Seite weitergeleitet werden")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void correctRedirectFromIndex() throws Exception {
    mvc.perform(get("/feedback")).andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/feedback/studenten"));
  }

  @Test
  @DisplayName("Studenten kommen auf die Uebersicht Seite")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void uebersichtSucess() throws Exception {
    mvc.perform(get("/feedback/studenten")).andExpect(status().is2xxSuccessful())
        .andExpect(view().name("studenten/veranstaltungen"));
  }

  @Test
  @DisplayName("Orga sollte nicht auf die student Uebersicht Seite kommen")
  @WithMockKeycloackAuth(roles = "orga", idToken = @WithIDToken(email = usermail))
  public void uebersichtFail() throws Exception {
    mvc.perform(get("/feedback/studenten")).andExpect(status().is4xxClientError());
  }

  @Test
  @DisplayName("Student sollte nicht auf die Orga Uebersicht Seite kommen")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void forbiddenAccessStudent() throws Exception {
    mvc.perform(get("/feedback/dozenten")).andExpect(status().is4xxClientError());
  }

  @Test
  @DisplayName("Studenten kommen auf die Frageboegen-Uebersicht Seite")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void fragebogen() throws Exception {
    mvc.perform(get("/feedback/studenten/frageboegen")
        .param("veranstaltungId" , "1"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("studenten/fragebogen-uebersicht"));
  }

  @Test
  @DisplayName("Studenten kommen auf die fragebogen-details Seite")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void fragebogenDetails() throws Exception {
    LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
    requestParams.add("veranstaltung", "1");
    requestParams.add("fragebogen", "0");

    mvc.perform(get("/feedback/studenten/frageboegen/details")
        .params(requestParams))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("studenten/fragebogen-details"));
  }

  @Test
  @DisplayName("Student soll Feedback abgeben können.")
  @Disabled
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void submitFeedback() throws Exception {

    String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
    HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
    CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());

    LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
    requestParams.add("veranstaltung", "1");
    requestParams.add("bogennr", "0");
    requestParams.add(csrfToken.getParameterName(), csrfToken.getToken());

    mvc.perform(post("/feedback/studenten/details/submit")
        .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
        .params(requestParams))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/feedback/studenten"));
  }
}
