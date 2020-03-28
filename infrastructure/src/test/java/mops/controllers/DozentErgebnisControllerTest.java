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
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
class DozentErgebnisControllerTest {
  private final transient String usermail = "orga@mail.de";
  private final transient String userrole = "orga";
  @Autowired
  private transient MockMvc mvc;

  @Test
  @Disabled
  @DisplayName("Dozent sollte Feedback sehen können")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void getAntwortenEinesFragebogens() throws Exception {
    mvc.perform(get("/feedback/dozenten/watch/1"))
        .andExpect(status().isOk())
        .andExpect(view().name("dozenten/ergebnisse"));
  }

  @Test
  @Disabled
  @DisplayName("Dozent sollte Textantwort bearbeiten können")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void bearbeiteTextAntwort() throws Exception {
    mvc.perform(get("/feedback/dozenten/watch/edit/1/1/1"))
        .andExpect(status().isOk())
        .andExpect(view().name("dozenten/zensieren"));
  }

  @Test
  @Disabled
  @DisplayName("Dozent sollte Textantwort speichern können")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void speichereTextAntwort() throws Exception {
    mvc.perform(post("/feedback/dozenten/watch/edit/1/1/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/feedback/dozenten/watch/1"));
  }

  @Test
  @Disabled
  @DisplayName("Dozent sollte Ergebnisse veröffentlichen können")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void veroeffentlichen() throws Exception {
    mvc.perform(post("/feedback/dozenten/watch/publish/1/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/feedback/dozenten/watch/1"));
  }
}
