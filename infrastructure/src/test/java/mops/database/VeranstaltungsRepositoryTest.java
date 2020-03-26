package mops.database;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings( {"PMD.JUnitTestsShouldIncludeAssert", "PMD.UnusedPrivateField"})
class VeranstaltungsRepositoryTest {

  private transient VeranstaltungJdbcRepository veranstaltungenRepo;
  private transient Translator translator;

  @BeforeEach
  void setUp() {
    this.veranstaltungenRepo = mock(VeranstaltungJdbcRepository.class);
    this.translator = mock(Translator.class);
  }


  @Test
  void getAll() {
    // TODO
  }

  @Test
  void getAllContaining() {
    // TODO
  }

  @Test
  void getVeranstaltungById() {
    // TODO
  }

  @Test
  void save() {
    // TODO
  }

  @Test
  void addStudentToVeranstaltungById() {
    // TODO
  }

  @Test
  void getAllFromStudent() {
    // TODO
  }

  @Test
  void getAllFromStudentContaining() {
    // TODO
  }

  @Test
  void getAllFromDozent() {
    // TODO
  }

  @Test
  void getAllFromDozentContaining() {
    // TODO
  }

  @Test
  void getFragebogenFromDozentById() {
    // TODO
  }

  @Test
  void getFragebogenByIdFromVeranstaltung() {
    // TODO
  }
}