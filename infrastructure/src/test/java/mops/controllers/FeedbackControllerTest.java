package mops.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.c4_soft.springaddons.test.security.context.support.WithIDToken;
import com.c4_soft.springaddons.test.security.context.support.WithMockKeycloackAuth;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class FeedbackControllerTest {
  @Autowired
  private transient MockMvc mvc;

  @Test
  @DisplayName("StudentIn sollte auf feedback/student/ weitergeleitet werden.")
  @WithMockKeycloackAuth(roles = "studentin", idToken = @WithIDToken(email = "user@mail.de"))
  public void redirectStudentinAufRichtigeSeite() throws Exception {
    mvc.perform(get("/feedback/")).andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/feedback/student/"));
  }

  @Test
  @DisplayName("Orga sollte auf feedback/dozenten weitergeleitet werden.")
  @WithMockKeycloackAuth(roles = "orga", idToken = @WithIDToken(email = "orga@mail.de"))
  public void redirectOrgaAufRichtigeSeite() throws Exception {
    mvc.perform(get("/feedback/")).andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/feedback/dozenten"));
  }

  @Test
  @DisplayName("Öffentliche Nutzer sollten auf die Index-Seite weitergeleitet werden.")
  @WithMockKeycloackAuth(roles = "", idToken = @WithIDToken(email = ""))
  public void publicUserCantAccessFeedback() throws Exception {
    mvc.perform(get("/feedback/"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("index"));
  }
}
