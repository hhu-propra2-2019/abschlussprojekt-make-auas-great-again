package mops.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Tests for SubmitController")
@SuppressWarnings( {"PMD.JUnitTestsShouldIncludeAssert", "PMD.JUnitAssertionsShouldIncludeMessage"})
class StudentControllerTest {
  @Autowired
  private transient MockMvc mvc;

  @Test
  @DisplayName("Test to show that POST is allowed and you are redirected to /feedback")
  @Disabled
  public void submit() throws Exception {
    mvc.perform(post("/feedback/student/details/submit/1")).andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/feedback/"));
  }

  @Test
  @DisplayName("Test to show that GET is not allowed to avoid side effects")
  public void getIsNotAllowed() throws Exception {
    mvc.perform(get("/feedback/student/details/submit/1")).andExpect(status().isMethodNotAllowed());
  }
}