package mops.controllers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.c4_soft.springaddons.test.security.context.support.WithIDToken;
import com.c4_soft.springaddons.test.security.context.support.WithMockKeycloackAuth;
import java.time.LocalDateTime;
import java.util.ArrayList;
import mops.Einheit;
import mops.Fragebogen;
import mops.Veranstaltung;
import mops.database.MockVeranstaltungsRepository;
import mops.fragen.Frage;
import mops.fragen.MultipleResponseFrage;
import mops.fragen.TextFrage;
import mops.rollen.Dozent;
import mops.rollen.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@SpringBootTest
@AutoConfigureMockMvc
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
class StudentControllerTest {
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

    Fragebogen fakeFragebogen = new Fragebogen(
        Long.valueOf("1"), fakeVeranstaltung.getName(), "Ben Jendisposto",
        new ArrayList<Frage>(),
        LocalDateTime.of(2020, 4, 1, 10, 0),
        LocalDateTime.of(2020, 8, 1, 2, 30),
        Einheit.VORLESUNG, new ArrayList<Student>());

    fakeFragebogen.addFrage(new TextFrage(Long.valueOf("1"), "Funktioniert das?"));
    fakeFragebogen.addFrage(new TextFrage(Long.valueOf("2"), "Feedback:"));

    fakeVeranstaltung.addFragebogen(fakeFragebogen);

    MockVeranstaltungsRepository mvr = new MockVeranstaltungsRepository();
    mvr.save(fakeVeranstaltung);
  }

  @Test
  @DisplayName("Student sollte auf die student_uebersicht Seite weitergeleitet werden")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void correctRedirectFromIndex() throws Exception {
    mvc.perform(get("/feedback")).andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/feedback/studenten"));
  }

  @Test
  @DisplayName("Studenten kommen auf die Uebersicht Seite")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void uebersichtSucess() throws Exception {
    mvc.perform(get("/feedback/studenten")).andExpect(status().is2xxSuccessful())
        .andExpect(view().name("studenten/veranstaltungen"));
  }

  @Test
  @DisplayName("Studenten kommen auf die Uebersicht Seite")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void uebersichtSucessWithSearch() throws Exception {
    mvc.perform(get("/feedback/studenten")
        .with(csrf()).param("search", "Suche"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("studenten/veranstaltungen"));
  }

  @Test
  @DisplayName("Orga sollte nicht auf die student Uebersicht Seite kommen")
  @WithMockKeycloackAuth(roles = "orga", idToken = @WithIDToken(email = usermail))
  public void uebersichtFail() throws Exception {
    mvc.perform(get("/feedback/studenten")).andExpect(status().is4xxClientError());
  }

  @Test
  @Disabled
  @DisplayName("Student sollte auf die 'student_details'-Seite weitergeleitet werden.")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void correctRedirectForDetails() throws Exception {
    mvc.perform(get("/feedback/studenten/details").param("id", "1"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("studenten/student_details"));
  }

  @Test
  @Disabled
  @DisplayName("Studenten sollen Fragenbögen angezeigt bekommen")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void frageboegenAnzeigen() throws Exception {
    mvc.perform(get("/feedback/student/frageboegen")
        .with(csrf())
        .param("search", "")
        .param("veranstaltungsId", "1"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("studenten/fragebogen_uebersicht"))
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  @DisplayName("Studenten sollen Details zu den Fragebögen angezeigt bekommen")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void fragebogenDetails() throws Exception {
    mvc.perform(get("/feedback/student/frageboegen/details")
        .param("fragebogen", "1")
        .param("veranstaltung", "1"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("studenten/fragebogen_uebersicht"));
  }


  @Test
  @Disabled
  @DisplayName("Student soll Feedback abgeben können.")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void correctRedirectForFeedbackPost() throws Exception {
    mvc.perform(post("/feedback/student/details/submit/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/feedback/student/"));
  }

  @Test
  @DisplayName("Student sollte nicht auf die Orga Uebersicht Seite kommen")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void forbiddenAccessStudent() throws Exception {
    mvc.perform(get("/feedback/dozenten")).andExpect(status().is4xxClientError());
  }
}
