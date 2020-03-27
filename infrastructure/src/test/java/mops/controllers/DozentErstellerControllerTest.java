package mops.controllers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
public class DozentErstellerControllerTest {
  private final transient String usermail = "orga@mail.de";
  private final transient String userrole = "orga";
  private static transient MockVeranstaltungsRepository mvr;

  @Autowired
  private transient MockMvc mvc;

  @BeforeAll
  public static void init() {
    Veranstaltung fakeVeranstaltung = new Veranstaltung(
        Long.valueOf(1L), "TestVeranstaltung", "SoSe2020",
        new Dozent("Christian Meile"),
        new ArrayList<Student>(), new ArrayList<Fragebogen>()
    );
    mvr = new MockVeranstaltungsRepository();
    mvr.save(fakeVeranstaltung);
  }

  @Test
  @Disabled
  @DisplayName("Dozent sollte neues Formular erstellen können")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void addNeuesFormular() throws Exception {
    mvc.perform(post("/feedback/dozenten/new")
        .with(csrf())
        .param("veranstaltungsid", "1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/feedback/dozenten/new/questions/"))
        .andDo(MockMvcResultHandlers.print());
    System.out.println(mvr.getVeranstaltungById(Long.valueOf(1)).getFrageboegen().size());
  }

  @Test
  @Disabled
  @DisplayName("Dozent sollte Metadaten ändern können")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void changeMetadaten() throws Exception {
    mvc.perform(post("/feedback/dozenten/new/meta/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/feedback/dozenten/new/questions/1"));
  }

  @Test
  @Disabled
  @DisplayName("Dozent sollte Fragen hinzufügen können")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void seiteUmFragenHizuzufuegen() throws Exception {
    mvc.perform(get("/feedback/dozenten/new/questions/1"))
        .andDo(print())
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("dozenten/fragenerstellen/"));
  }

  @Test
  @Disabled
  @DisplayName("Dozent sollte Fragen löschen können")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void loescheFrageAusFragebogen() throws Exception {
    mvc.perform(post("/feedback/dozenten/new/questions/1/1"))
        .andDo(print())
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/feedback/dozenten/new/questions/1"));
  }

  @Test
  @Disabled
  @DisplayName("Dozent sollte Textfrage hinzufügen können")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void addTextfrage() throws Exception {
    mvc.perform(post("/feedback/dozenten/new/questions/add/1"))
        .andDo(print())
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/feedback/dozenten/new/questions/1"));
  }

  @Test
  @Disabled
  @DisplayName("Dozent sollte Antwortmöglichkeiten hinzufügen können")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void seiteUmAntwortmoeglichkeitenHinzuzufuegen() throws Exception {
    mvc.perform(post("/feedback/dozenten/new/questions/edit/1/1"))
        .andDo(print())
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("dozenten/multiplechoiceedit"));
  }

  @Test
  @Disabled
  @DisplayName("Dozent sollte neue MultipleChoiceAntwort hinzufügen können")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void neueMultipleChoiceAntwort() throws Exception {
    mvc.perform(post("/feedback/dozenten/new/questions/mc/add/1/1"))
        .andDo(print())
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/feedback/dozenten/new/questions/edit/1/1"));
  }

  @Test
  @Disabled
  @DisplayName("Dozent sollte MultipleChoiceAntwort löschen können")
  @WithMockKeycloackAuth(roles = userrole, idToken = @WithIDToken(email = usermail))
  public void loescheMultipleChoiceAntwort() throws Exception {
    mvc.perform(post("/feedback/dozenten/new/questions/mc/delete/1/1/1"))
        .andDo(print())
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name(
            "redirect:/feedback/dozenten/new/questions/edit/1/1"));
  }
}
