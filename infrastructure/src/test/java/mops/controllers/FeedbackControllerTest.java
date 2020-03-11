package mops.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
class FeedbackControllerTest {

  @Autowired
  transient MockMvc mvc;

  @Disabled
  @Test
  void uebersicht() throws Exception {
    mvc.perform(
        get("/feedback/")
            .param("search", "")
            .param("token", "hdubuztfzufuztfztfbztfuzft"))
        .andExpect(status().isOk())
        .andExpect(view().name("uebersicht"));
  }

  @Disabled
  @Test
  void fragebogen() throws Exception {
    mvc.perform(get("/feedback/details")
        .param("id", "1"))
        .andExpect(status().isOk())
        .andExpect(view().name("details"));
  }
}