package mops.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import com.c4_soft.springaddons.test.security.context.support.WithIDToken;
import com.c4_soft.springaddons.test.security.context.support.WithMockKeycloackAuth;
import mops.Kontaktformular;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
class StudentControllerTest {
  @Autowired
  private transient MockMvc mvc;

  private final transient String usermail = "user@mail.de";
  private final transient String userrole = "studentin";

  @Test
  @DisplayName("Student sollte auf die student_uebersicht Seite weitergeleitet werden")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void correctRedirectFromIndex() throws Exception {
    mvc.perform(get("/feedback/"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/feedback/student/"));
  }

  @Test
  @DisplayName("Studenten kommen auf die Uebersicht Seite")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void uebersichtSucess() throws Exception {
    mvc.perform(get("/feedback/student/"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("studenten/student_uebersicht"));
  }

  @Test
  @DisplayName("Orga sollte nicht auf die student Uebersicht Seite kommen")
  @WithMockKeycloackAuth(roles = "orga", idToken = @WithIDToken(email = usermail))
  public void uebersichtFail() throws Exception {
    mvc.perform(get("/feedback/student/")).andExpect(status().is4xxClientError());
  }

  @Test
  @DisplayName("Student sollte auf die 'student_details'-Seite weitergeleitet werden.")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void correctRedirectForDetails() throws Exception {
    mvc.perform(get("/feedback/student/details").param("id", "1"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("studenten/student_details"));
  }

  @Test
  @DisplayName("Student sollte auf die 'kontakt'-Seite weitergeleitet werden.")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void correctRedirectForKontakt() throws Exception {
    mvc.perform(get("/feedback/student/kontakt"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("/studenten/kontakt"));
  }

  @Test
  @DisplayName("Student sollte auf die 'ergebnis'-Seite weitergeleitet werden.")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void correctRedirectForErgebnis() throws Exception {
    mvc.perform(get("/feedback/student/ergebnis"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("/studenten/ergebnis"));
  }

  @Test
  @DisplayName("Student sollte auf die 'ergebnisUebersicht'-Seite weitergeleitet werden.")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void correctRedirectForErgebnisUebersicht() throws Exception {
    mvc.perform(get("/feedback/student/ergebnisUebersicht").param("id", "1"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("/studenten/ergebnisUebersicht"));
  }

  @Test
  @Disabled
  @DisplayName("Student sollte auf die 'kontakt'-Seite weitergeleitet werden.")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void correctRedirectForKontaktPost() throws Exception {
    mvc.perform(post("/feedback/student/kontakt").flashAttr("kontakt", new Kontaktformular()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("/feedback/student/"));
  }

  @Test
  @Disabled
  @DisplayName("Student soll Feedback abgeben können.")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void correctRedirectForFeedbackPost() throws Exception {
    mvc.perform(post("/feedback/student/details/submit/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/feedback/student/"));
  }
}
