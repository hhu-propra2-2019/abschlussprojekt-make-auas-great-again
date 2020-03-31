package mops.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
public class CustomErrorControllerTest {

  private final transient String userrole = "studentin";
  private final transient String usermail = "user@mail.de";

  @Autowired
  private transient MockMvc mvc;

  @Disabled
  @Test
  @DisplayName("Weiterleitung zu Errorpage wenn die Dozenten Page von studenten aufgerufen wird")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void studentErrorPageDisplay() throws Exception {
    mvc.perform(get("/dozenten")).andExpect(status().is4xxClientError())
        .andExpect(redirectedUrl("errorpages/.."));
  }
}