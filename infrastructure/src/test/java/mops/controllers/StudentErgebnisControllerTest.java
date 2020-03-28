package mops.controllers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.c4_soft.springaddons.test.security.context.support.WithIDToken;
import com.c4_soft.springaddons.test.security.context.support.WithMockKeycloackAuth;
import java.util.ArrayList;
import mops.Fragebogen;
import mops.Veranstaltung;
import mops.database.MockVeranstaltungsRepository;
import mops.rollen.Dozent;
import mops.rollen.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
class StudentErgebnisControllerTest {
  private final transient String usermail = "user@mail.de";
  private final transient String userrole = "studentin";
  @Autowired
  private transient MockMvc mvc;

  @BeforeAll
  public static void init() {
    Veranstaltung fakeVeranstaltung = new Veranstaltung(
        Long.valueOf(1L), "TestVeranstaltung", "SoSe2020",
        new Dozent("Christian Meile"),
        new ArrayList<Student>(), new ArrayList<Fragebogen>()
    );
    MockVeranstaltungsRepository mvr = new MockVeranstaltungsRepository();
    mvr.save(fakeVeranstaltung);
  }

  @Test
  @DisplayName("Student sollte auf die 'ergebnisUebersicht'-Seite weitergeleitet werden.")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void correctRedirectForErgebnisUebersicht() throws Exception {
    mvc.perform(get("/feedback/studenten/ergebnis/frageboegen")
        .param("veranstaltungId", "1"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("studenten/ergebnis-frageboegen"));
  }

  @Test
  @DisplayName("Student sollte auf die 'ergebnisUebersicht'-Seite weitergeleitet werden.")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void correctRedirectForErgebnisUebersichtWithSearch() throws Exception {
    mvc.perform(get("/feedback/studenten/ergebnis/frageboegen")
        .param("veranstaltungId", "1")
        .with(csrf()).param("search", "test"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("studenten/ergebnis-frageboegen"));
  }

  @Test
  @DisplayName("Student sollte auf die 'ergebnis'-Seite weitergeleitet werden.")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void correctRedirectForErgebnis() throws Exception {
    mvc.perform(get("/feedback/studenten/ergebnis"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("studenten/ergebnis"));
  }

}
