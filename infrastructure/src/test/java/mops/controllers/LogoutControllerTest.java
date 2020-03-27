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
public class LogoutControllerTest {
  private final transient String usermail = "user@mail.de";
  private final transient String orgamail = "orga@mail.de";

  @Autowired
  private transient MockMvc mvc;

  @Test
  @DisplayName("Student sollte nach dem logout zu dem index seite weitergeleitet werden")
  @WithMockKeycloackAuth(roles = "studentin", idToken = @WithIDToken(email = usermail))
  public void logoutStudentIndexRedirection() throws Exception {
    mvc.perform(get("/logout")).andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/"));
  }

  @Test
  @DisplayName("Orga sollte nach dem logout zu dem index seite weitergeleitet werden")
  @WithMockKeycloackAuth(roles = "orga", idToken = @WithIDToken(email = orgamail))
  public void logoutOrgaIndexRedirection() throws Exception {
    mvc.perform(get("/logout")).andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/"));
  }
}