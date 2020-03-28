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
import org.springframework.util.LinkedMultiValueMap;

@SpringBootTest
@AutoConfigureMockMvc
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class StudentEgebnisControllerTest {
  private final transient String usermail = "user@mail.de";
  private final transient String userrole = "studentin";
  @Autowired
  private transient MockMvc mvc;

  @Test
  @DisplayName("Student sollte auf die 'ergebnis'-Seite weitergeleitet werden.")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void correctRedirectForErgebnis() throws Exception {
    mvc.perform(get("/feedback/studenten/ergebnis"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("studenten/ergebnis"));
  }

  @Test
  @DisplayName("Studenten kommen auf die ergebnis/FrageboegenUebersicht Seite")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void ergebnisBoegen() throws Exception {
    mvc.perform(get("/feedback/studenten/ergebnis/frageboegen")
        .param("veranstaltungId" , "1"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("studenten/ergebnis-frageboegen"));
  }

  @Test
  @DisplayName("Studenten kommen auf die ergebnis details Seite")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void ergebnisUebersicht() throws Exception {
    LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
    requestParams.add("veranstaltung", "1");
    requestParams.add("fragebogen", "0");

    mvc.perform(get("/feedback/studenten/ergebnis/details")
        .params(requestParams))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("/studenten/ergebnis-details"));
  }
}
