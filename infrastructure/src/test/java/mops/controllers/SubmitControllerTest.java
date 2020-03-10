package mops.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@DisplayName("Tests for SubmitController")
public class SubmitControllerTest {
  @Autowired
  private transient MockMvc mvc;

  @Test
  public void submit() throws Exception {
    mvc.perform(post("/feedback/details/submit/1")).andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/feedback/"));
  }

  @Test
  public void getIsNotAllowed() throws Exception {
    mvc.perform(get("/feedback/details/submit/1")).andExpect(status().isMethodNotAllowed());
  }
}
