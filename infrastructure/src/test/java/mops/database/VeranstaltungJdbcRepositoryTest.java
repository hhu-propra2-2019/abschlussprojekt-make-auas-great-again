package mops.database;


import static mops.Einheit.PRAKTIKUM;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import mops.database.dto.AntwortDto;
import mops.database.dto.AuswahlDto;
import mops.database.dto.DOrganisiertVDto;
import mops.database.dto.DozentDto;
import mops.database.dto.FrageDto;
import mops.database.dto.FragebogenDto;
import mops.database.dto.SBelegtVDto;
import mops.database.dto.StudentDto;
import mops.database.dto.VeranstaltungDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

@SuppressWarnings("PMD.DataflowAnomalyAnalysis" + "PMD.JUnitTestContainsTooManyAsserts")
@Disabled
@DataJdbcTest
public class VeranstaltungJdbcRepositoryTest {

  @Autowired
  private transient VeranstaltungJdbcRepository repository;
  @Autowired
  private transient StudentenJdbcRepository studentenRepo;
  @Autowired
  private transient DozentenJdbcRepository dozentenRepo;
  @Autowired
  private transient FragebogenJdbcRepository fragebogenRepo;

  @Test
  void findVeranstaltungenForDozentContainingTest() {
    DozentDto d1 = DozentDto.create("dozent14", "jens", "ben");
    dozentenRepo.save(d1);
    VeranstaltungDto v1 = VeranstaltungDto.create("veran1", "WS2014");
    VeranstaltungDto v2 = VeranstaltungDto.create("progra", "WS2023");
    v1.addDozent(d1);
    v2.addDozent(d1);
    repository.save(v1);
    repository.save(v2);
    ArrayList<Long> liste = new ArrayList<>();
    Iterable<VeranstaltungDto> veranstaltungen = repository.getAllFromDozentContaining(
        dozentenRepo.findId("dozent14"), "ver");
    veranstaltungen.forEach(v -> assertThat(v.getDozenten().contains(d1)));
    veranstaltungen.forEach(v -> liste.add(v.getId()));
    assertThat(liste).contains(v1.getId());
    assertThat(liste).doesNotContain(v2.getId());
  }

  @Test
  void findVeranstaltungenForStudentContainingTest() {
    StudentDto s1 = StudentDto.create("student13");
    DozentDto dozent = DozentDto.create("dozent2", "vor2", "nach2");
    studentenRepo.save(s1);
    dozentenRepo.save(dozent);
    VeranstaltungDto v1 = VeranstaltungDto.create("veran2", "WS2014");
    VeranstaltungDto v2 = VeranstaltungDto.create("progra", "WS2023");
    v1.addDozent(dozent);
    v2.addDozent(dozent);
    v1.addStudent(s1);
    v2.addStudent(s1);
    repository.save(v1);
    repository.save(v2);
    ArrayList<Long> liste = new ArrayList<>();
    Iterable<VeranstaltungDto> veranstaltungen = repository.getAllFromStudentContaining(
        studentenRepo.findId("student13"), "ver");
    veranstaltungen.forEach(v -> liste.add(v.getId()));
    veranstaltungen.forEach(v -> assertThat(v.getStudenten().contains(s1)));
    assertThat(liste).contains(v1.getId());
    assertThat(liste).doesNotContain(v2.getId());
  }

  @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
  @Test
  void findVeranstaltungenForDozentTest() {
    DozentDto d1 = DozentDto.create("dozent33", "jens", "ben");
    dozentenRepo.save(d1);
    VeranstaltungDto v1 = VeranstaltungDto.create("veran1", "WS2018");
    v1.addDozent(d1);
    VeranstaltungDto v2 = VeranstaltungDto.create("veran2", "WS2022");
    v2.addDozent(d1);
    repository.save(v1);
    repository.save(v2);
    Iterable<VeranstaltungDto> veranstaltungen = repository.getAllFromDozent(
        dozentenRepo.findId("dozent33"));
    veranstaltungen.forEach(v -> assertThat(v.getDozenten().contains(d1)));
  }

  @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
  @Test
  void findVeranstaltungenForStudentTest() {
    StudentDto s1 = StudentDto.create("student31");
    studentenRepo.save(s1);
    DozentDto dozent = DozentDto.create("orga6", "vor", "nach");
    dozentenRepo.save(dozent);
    VeranstaltungDto v1 = VeranstaltungDto.create("veran1", "WS2015");
    VeranstaltungDto v2 = VeranstaltungDto.create("veran2", "WS2017");
    v1.addStudent(s1);
    v2.addStudent(s1);
    v1.addDozent(dozent);
    v2.addDozent(dozent);
    repository.save(v1);
    repository.save(v2);
    Iterable<VeranstaltungDto> veranstaltungen = repository.getAllFromStudent(
        studentenRepo.findId("student31"));
    veranstaltungen.forEach(v -> assertThat(v.getStudenten().contains(s1)));
  }

  @Test
  void findVeranstaltungContainingTest() {
    DozentDto dozent = DozentDto.create("dozent11", "vorn", "nachn");
    dozentenRepo.save(dozent);
    VeranstaltungDto v1 = VeranstaltungDto.create("aldat", "WS2015");
    VeranstaltungDto v2 = VeranstaltungDto.create("prog", "WS2017");
    VeranstaltungDto v3 = VeranstaltungDto.create("dalten", "WS2016");
    v1.addDozent(dozent);
    v2.addDozent(dozent);
    v3.addDozent(dozent);
    repository.save(v1);
    repository.save(v2);
    repository.save(v3);
    ArrayList<Long> liste = new ArrayList<>();
    Iterable<VeranstaltungDto> veranstaltungen = repository.getAllContaining("al");
    veranstaltungen.forEach(v -> liste.add(v.getId()));
    assertThat(liste).contains(v1.getId());
    assertThat(liste).doesNotContain(v2.getId());
    assertThat(liste).contains(v3.getId());
  }

  @Test
  void findDozentByUsernameTest() {
    DozentDto dozent1 = DozentDto.create("orga11", "jens", "ben");
    DozentDto dozent2 = DozentDto.create("orga12", "ann", "kathrin");
    dozentenRepo.save(dozent1);
    dozentenRepo.save(dozent2);
    Long id1 = dozentenRepo.findId("orga11");
    Long id2 = dozentenRepo.findId("orga12");
    assertThat(dozent1.getId()).isEqualTo(id1);
    assertThat(dozent2.getId()).isEqualTo(id2);
  }

  @Test
  void findStudentByUsernameTest() {
    StudentDto student1 = StudentDto.create("student11");
    StudentDto student2 = StudentDto.create("student22");
    studentenRepo.save(student1);
    studentenRepo.save(student2);
    Long id1 = studentenRepo.findId("student11");
    Long id2 = studentenRepo.findId("student22");
    assertThat(student1.getId()).isEqualTo(id1);
    assertThat(student2.getId()).isEqualTo(id2);
  }

  @Test
  void addAntwortenToFragenToFragebogenToVeranstaltungKnowingId() {
    FragebogenDto fragebogen = FragebogenDto.create(
        "Fragebogen zum Praktikum", PRAKTIKUM, "2020-01-01 12:00:00", "2020-05-01 12:00:00");
    FrageDto frage2 = FrageDto.createMultipleResponsefrage("Wo?");
    AuswahlDto auswahl1 = AuswahlDto.create("Ja");
    AuswahlDto auswahl2 = AuswahlDto.create("Nein");
    frage2.addChoice(auswahl1);
    frage2.addChoice(auswahl2);
    fragebogen.addFrage(frage2);
    VeranstaltungDto veranstaltung = VeranstaltungDto.create("propra5", "WS2019");
    DozentDto dozent = DozentDto.create("dozent11", "chris", "r");
    dozentenRepo.save(dozent);
    veranstaltung.addDozent(dozent);
    repository.save(veranstaltung);
    veranstaltung.addFragebogen(fragebogen);
    repository.save(veranstaltung);
    fragebogenRepo.save(fragebogen);
    FragebogenDto fragebogenReturn = fragebogenRepo.findById(1L).get();
    Set<FrageDto> fragen = fragebogenReturn.getFragen();
    AntwortDto antwort2 = AntwortDto.createMultipleChoiceAntwort();
    for (FrageDto frage : fragen) {
      antwort2.addAntwortToMultipleChoice(AuswahlDto.create(auswahl1.getAuswahltext()));
      frage.addAnwort(antwort2);
    }
    fragebogenReturn.setFragen(fragen);
    fragebogenRepo.save(fragebogenReturn);
    FragebogenDto fragebogenReturn2 = fragebogenRepo.findById(1L).get();
    Set<FrageDto> fragen2 = fragebogenReturn2.getFragen();
    Set<AntwortDto> antworten = new HashSet<>();
    for (FrageDto frage : fragen2) {
      antworten.addAll(frage.getAntworten());
    }
    assertThat(antworten).isNotEmpty();
  }

  @Test
  void addFragenToFragebogenToVeranstaltung() {
    FragebogenDto fragebogen = FragebogenDto.create(
        "Fragebogen zum Praktikum", PRAKTIKUM, "2020-01-01 12:00:00", "2020-05-01 12:00:00");
    FrageDto frage1 = FrageDto.createTextfrage("Was?");
    FrageDto frage2 = FrageDto.createMultipleResponsefrage("Wo?");
    fragebogen.addFrage(frage1);
    fragebogen.addFrage(frage2);
    VeranstaltungDto veranstaltung = VeranstaltungDto.create("propra4", "SoSe2018");
    DozentDto dozent = DozentDto.create("dozent7", "vorname", "nachname");
    dozentenRepo.save(dozent);
    veranstaltung.addDozent(dozent);
    repository.save(veranstaltung);
    veranstaltung.addFragebogen(fragebogen);
    repository.save(veranstaltung);
    List<Set<FrageDto>> fragen = new ArrayList<>();
    List<Set<FragebogenDto>> frageboegen = new ArrayList<>();
    Iterable<VeranstaltungDto> result = repository.findAll();
    result.forEach(dto -> frageboegen.add(dto.getFrageboegen()));
    List<FragebogenDto> bogen = new ArrayList<FragebogenDto>(frageboegen.get(0));
    bogen.forEach(b -> fragen.add(b.getFragen()));
    assertThat(fragen).isNotEmpty();
  }

  @Test
  void addFragebogenToVeranstaltungTest() {
    FragebogenDto fragebogen = FragebogenDto.create(
        "Fragebogen zum Praktikum", PRAKTIKUM, "2020-01-01 12:00:00", "2020-05-01 12:00:00");
    FrageDto frage = FrageDto.createTextfrage("Was?");
    fragebogen.addFrage(frage);
    VeranstaltungDto veranstaltung = VeranstaltungDto.create("propra0", "Sose2020");
    DozentDto dozent = DozentDto.create("dozent13", "vor", "nach");
    dozentenRepo.save(dozent);
    veranstaltung.addDozent(dozent);
    repository.save(veranstaltung);
    veranstaltung.addFragebogen(fragebogen);
    repository.save(veranstaltung);
    List<Set<FragebogenDto>> frageboegen = new ArrayList<>();
    Iterable<VeranstaltungDto> result = repository.findAll();
    result.forEach(dto -> frageboegen.add(dto.getFrageboegen()));
    assertThat(frageboegen).isNotEmpty();
  }

  @Test
  void addDozentToVeranstaltungTest() {
    DozentDto dozent = DozentDto.create("dozent12", "chris", "m");
    dozentenRepo.save(dozent);
    VeranstaltungDto veranstaltung = VeranstaltungDto.create("propra1", "SoSe2019");
    veranstaltung.addDozent(dozent);
    repository.save(veranstaltung);
    List<Set<DOrganisiertVDto>> dozenten = new ArrayList<>();
    Iterable<VeranstaltungDto> result = repository.findAll();
    result.forEach(dto -> dozenten.add(dto.getDozenten()));
    assertThat(dozenten).isNotEmpty();
  }

  @Test
  void addStudentenToVeranstaltungTest() {
    StudentDto student1 = StudentDto.create("student11");
    studentenRepo.save(student1);
    VeranstaltungDto veranstaltung = VeranstaltungDto.create("propra2", "WS2019");
    veranstaltung.addStudent(student1);
    DozentDto dozent = DozentDto.create("dozent12", "vor", "nach");
    dozentenRepo.save(dozent);
    veranstaltung.addDozent(dozent);
    repository.save(veranstaltung);
    List<Set<SBelegtVDto>> studenten = new ArrayList<>();
    Iterable<VeranstaltungDto> result = repository.findAll();
    result.forEach(dto -> studenten.add(dto.getStudenten()));
    assertThat(studenten).isNotEmpty();
  }

  @Test
  void saveVeranstaltungTest() {
    DozentDto dozent = DozentDto.create("orga", "christan", "meter");
    dozentenRepo.save(dozent);
    VeranstaltungDto veranstaltung = VeranstaltungDto.create("Propra3", "WS2018");
    veranstaltung.addDozent(dozent);
    repository.save(veranstaltung);
    ArrayList<VeranstaltungDto> veranstaltungen = new ArrayList<>();
    Iterable<VeranstaltungDto> result = repository.findAll();
    result.forEach(dto -> veranstaltungen.add(dto));
    assertThat(veranstaltungen).isNotEmpty();
  }
}
