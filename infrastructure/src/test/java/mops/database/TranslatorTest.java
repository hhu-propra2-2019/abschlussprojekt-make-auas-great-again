package mops.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;
import mops.Einheit;
import mops.Fragebogen;
import mops.Veranstaltung;
import mops.VeranstaltungsService;
import mops.database.dto.DozentDto;
import mops.database.dto.FragebogenDto;
import mops.database.dto.StudentDto;
import mops.database.dto.VeranstaltungDto;
import mops.rollen.Dozent;
import mops.rollen.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TranslatorTest {
  private transient StudentenJdbcRepository studentenRepo;
  private transient DozentenJdbcRepository dozentenRepo;
  private transient VeranstaltungsService veranstaltungsService;
  private transient Translator translator;

  @BeforeEach
  void setUp() {
    this.studentenRepo = mock(StudentenJdbcRepository.class);
    this.dozentenRepo = mock(DozentenJdbcRepository.class);
    this.translator = new Translator(studentenRepo, dozentenRepo);
    this.veranstaltungsService = new VeranstaltungsService();
  }

  @Test
  void loadVeranstaltungTest() {
    VeranstaltungDto mockVeranstaltung = VeranstaltungDto.create("ProPra", "SOSE2020");
    StudentDto mockStudent = StudentDto.create("studentin");
    DozentDto mockDozent = DozentDto.create("jensben", "Jens",
        "Bendisposto", "Herr");
    FragebogenDto mockFragebogen = FragebogenDto.create("Vorlesungen", Einheit.VORLESUNG,
        LocalDateTime.now().toString(), LocalDateTime.now().plusMinutes(5L).toString());
    mockVeranstaltung.addStudent(mockStudent);
    mockVeranstaltung.addDozent(mockDozent);
    mockVeranstaltung.addFragebogen(mockFragebogen);

    Student student = new Student("studentin");
    Dozent dozent = new Dozent("jensben");
    Fragebogen fragebogen = new Fragebogen("Vorlesungen");

    Veranstaltung veranstaltung = translator.loadVeranstaltung(mockVeranstaltung);

    assertTrue(veranstaltung.contains("ProPra"));
    assertTrue(veranstaltung.hasStudent(student));
    assertTrue(veranstaltung.hasDozent(dozent));
    assertTrue(veranstaltung.getFrageboegen().contains(fragebogen));
  }

  @Test
  void createVeranstaltungDtoTest() {
    Veranstaltung veranstaltung = veranstaltungsService.randomVeranstaltungen().get(0);

    VeranstaltungDto veranstaltungDto = translator.createVeranstaltungDto(veranstaltung);

    assertEquals("SOSE2019", veranstaltungDto.getSemester());
  }
}