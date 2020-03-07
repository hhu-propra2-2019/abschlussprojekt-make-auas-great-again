package mops.database;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.List;
import mops.Fragebogen;
import org.junit.jupiter.api.Test;

class MockFragebogenRepositoryImplTest {
  FragebogenRepository frageboegen = new MockFragebogenRepositoryImpl();

  @Test
  void getFragebogenByIdTest() {
    Fragebogen fragebogen = frageboegen.getFragebogenById(1L);
    assertThat(fragebogen).isInstanceOf(Fragebogen.class);
    assertThat(fragebogen.getProfessorenname()).isEqualTo("Jens Bendisposto");
  }

  @Test
  void getAllTest() {
    List<Fragebogen> fragebogenList = frageboegen.getAll();
    assertThat(fragebogenList).isNotEmpty();
  }
}