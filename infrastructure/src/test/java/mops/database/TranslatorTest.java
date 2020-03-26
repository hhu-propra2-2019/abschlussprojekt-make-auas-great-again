package mops.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
  @SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
  void loadVeranstaltungTest() {
    VeranstaltungDto mockVeranstaltung = VeranstaltungDto.create("ProPra", "SOSE2020");
    StudentDto mockStudent = StudentDto.create("studentin");
    when(studentenRepo.findById(any())).thenReturn(java.util.Optional.of(mockStudent));
    DozentDto mockDozent1 = DozentDto.create("jensben", "Jens",
        "Bendisposto");
    DozentDto mockDozent2 = DozentDto.create("jensben", "Jens",
        "Bendisposto");
    when(dozentenRepo.findById(any())).thenReturn(java.util.Optional.of(mockDozent1));
    FragebogenDto mockFragebogen = FragebogenDto.create("Vorlesungen", Einheit.VORLESUNG,
        LocalDateTime.now().toString(), LocalDateTime.now().plusMinutes(5L).toString());
    mockFragebogen.setId(1L);
    mockVeranstaltung.addStudent(mockStudent);
    mockVeranstaltung.addFragebogen(mockFragebogen);
    mockVeranstaltung.addDozent(mockDozent1);
    mockVeranstaltung.addDozent(mockDozent2);

    Student student = new Student("studentin");
    Dozent dozent = new Dozent("jensben");
    Fragebogen fragebogen = new Fragebogen("Vorlesungen");
    fragebogen.setBogennr(1L);

    Veranstaltung veranstaltung = translator.loadVeranstaltung(mockVeranstaltung);

    assertTrue(veranstaltung.contains("ProPra"), "Veranstaltung nicht gefunden");
    assertTrue(veranstaltung.hasStudent(student), "student nicht gefunden");
    assertTrue(veranstaltung.hasDozent(dozent), "dozent nicht gefunden");
    assertTrue(veranstaltung.getFrageboegen().contains(fragebogen), "fragebogen nicht gefunden");
  }

  @Test
  void createVeranstaltungDtoTest() {
    Veranstaltung veranstaltung = veranstaltungsService.randomVeranstaltungen().get(0);

    VeranstaltungDto veranstaltungDto = translator.createVeranstaltungDto(veranstaltung);

    assertEquals("SOSE2019", veranstaltungDto.getSemester(), "Semester falsch");
  }
}