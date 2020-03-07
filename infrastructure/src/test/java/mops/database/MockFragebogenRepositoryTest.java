package mops.database;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.List;
import mops.Fragebogen;
import mops.controllers.FragebogenRepository;
import org.junit.jupiter.api.Test;

class MockFragebogenRepositoryTest {
  private transient FragebogenRepository frageboegen = new MockFragebogenRepository();

  @Test
  void getFragebogenByIdTest() {
    Fragebogen fragebogen = frageboegen.getFragebogenById(1L);
    assertThat(fragebogen.getProfessorenname()).isEqualTo("Jens Bendisposto");
  }

  @Test
  void getAllTest() {
    List<Fragebogen> fragebogenList = frageboegen.getAll();
    assertThat(fragebogenList).isNotEmpty();
  }
}