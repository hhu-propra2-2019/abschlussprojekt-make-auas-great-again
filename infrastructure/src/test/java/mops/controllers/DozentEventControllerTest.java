package mops.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
public class DozentEventControllerTest {
  private final transient String orgamail = "orga@mail.de";

  @Autowired
  private transient MockMvc mvc;

  @Test
  @DisplayName("Orga sollten auf dozenten/dozent weitergeleitet werden")
  @WithMockKeycloackAuth(roles = "orga", idToken = @WithIDToken(email = orgamail))
  public void redirectOrgaToCorrectPage() throws Exception {
    mvc.perform(get("/feedback/dozenten"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("dozenten/dozent"));
  }

  @Test
  @DisplayName("VeranstaltungsErstellerseite soll geöffnet werden")
  @WithMockKeycloackAuth(roles = "orga", idToken = @WithIDToken(email = orgamail))
  public void correctRedirectForVeranstaltungsErstellerSeite() throws Exception {
    mvc.perform(get("/feedback/dozenten/event/new"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("dozenten/neueveranstaltung"));
  }

  @Test
  @DisplayName("Orga sollten auf Veranstaltungsdetails zugreifen können")
  @WithMockKeycloackAuth(roles = "orga", idToken = @WithIDToken(email = orgamail))
  public void correctRedirectForVeranstaltungsDetails() throws Exception {
    mvc.perform(get("/feedback/dozenten/event/1"))
        .andDo(print())
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("dozenten/veranstaltung"));
  }
}
