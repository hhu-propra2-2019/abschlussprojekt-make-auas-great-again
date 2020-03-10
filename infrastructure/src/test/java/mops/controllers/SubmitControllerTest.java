package mops.controllers;

import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@DisplayName("Tests for SubmitController")
public class SubmitControllerTest {
  @Autowired
  private transient MockMvc mvc;
}
