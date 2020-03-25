package mops.database;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
  }

  @Test
  void getAllContaining() {
  }

  @Test
  void getVeranstaltungById() {
  }

  @Test
  void save() {
  }

  @Test
  void addStudentToVeranstaltungById() {
  }

  @Test
  void getAllFromStudent() {
  }

  @Test
  void getAllFromStudentContaining() {
  }

  @Test
  void getAllFromDozent() {
  }

  @Test
  void getAllFromDozentContaining() {
  }

  @Test
  void getFragebogenFromDozentById() {
  }

  @Test
  void getFragebogenByIdFromVeranstaltung() {
  }
}