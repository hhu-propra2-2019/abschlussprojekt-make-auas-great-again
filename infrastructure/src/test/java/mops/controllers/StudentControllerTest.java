package mops.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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
class StudentControllerTest {
  @Autowired
  private transient MockMvc mvc;

  @Test
  @DisplayName("Studenten sollten auf die Uebersicht kommen")
  @WithMockKeycloackAuth(roles = "studentin", idToken = @WithIDToken(email = "user@mail.de"))
  public void studentUebersicht() throws Exception {
    mvc.perform(post("/feedback/student/")).andExpect(status().is2xxSuccessful());
  }

  @Test
  @DisplayName("Test to show that GET is not allowed to avoid side effects")
  public void getIsNotAllowed() throws Exception {
    mvc.perform(get("/feedback/student/details/submit/1")).andExpect(status().isMethodNotAllowed());
  }
}
