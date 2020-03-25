package mops.database;


import static mops.Einheit.PRAKTIKUM;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import mops.database.dto.AntwortDto;
import mops.database.dto.AuswahlDto;
import mops.database.dto.DozentDto;
import mops.database.dto.FrageDto;
import mops.database.dto.FragebogenDto;
import mops.database.dto.SBelegtVDto;
import mops.database.dto.StudentDto;
import mops.database.dto.VeranstaltungDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
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
    DozentDto d1 = DozentDto.create("dozent1", "jens", "ben");
    dozentenRepo.save(d1);
    VeranstaltungDto v1 = VeranstaltungDto.create("veran1", "WS2020");
    VeranstaltungDto v2 = VeranstaltungDto.create("progra", "WS2021");
    d1.addVeranstaltung(v1);
    d1.addVeranstaltung(v2);
    dozentenRepo.save(d1);
    repository.save(v1);
    repository.save(v2);
    ArrayList<VeranstaltungDto> liste = new ArrayList<>();
    Iterable<VeranstaltungDto> veranstaltungen = repository.getAllFromDozentContaining(
        dozentenRepo.findId("dozent1"), "ver");
    veranstaltungen.forEach(v -> assertThat(d1.getVeranstaltungen().contains(v)));
    veranstaltungen.forEach(v -> liste.add(v));
    assertThat(liste).contains(v1);
    assertThat(liste).doesNotContain(v2);
  }

  @Test
  void findVeranstaltungenForStudentContainingTest() {
    StudentDto s1 = StudentDto.create("student1");
    DozentDto dozent = DozentDto.create("dozent", "vor", "nach");
    VeranstaltungDto v1 = VeranstaltungDto.create("veran1", "WS2020");
    VeranstaltungDto v2 = VeranstaltungDto.create("progra", "WS2021");
    studentenRepo.save(s1);
    dozent.addVeranstaltung(v1);
    dozent.addVeranstaltung(v2);
    dozentenRepo.save(dozent);
    v1.addStudent(s1);
    v2.addStudent(s1);
    studentenRepo.save(s1);
    repository.save(v1);
    repository.save(v2);
    ArrayList<Long> liste = new ArrayList<>();
    Iterable<VeranstaltungDto> veranstaltungen = repository.getAllFromStudentContaining(
        studentenRepo.findId("student1"), "ver");
    veranstaltungen.forEach(v -> liste.add(v.getId()));
    veranstaltungen.forEach(v -> assertThat(v.getStudenten().contains(s1)));
    assertThat(liste).contains(v1.getId());
    assertThat(liste).doesNotContain(v2.getId());
  }

  @Test
  void findVeranstaltungenForDozentTest() {
    DozentDto d1 = DozentDto.create("dozent1", "jens", "ben");
    dozentenRepo.save(d1);
    VeranstaltungDto v1 = VeranstaltungDto.create("veran1", "WS2020");
    d1.addVeranstaltung(v1);
    VeranstaltungDto v2 = VeranstaltungDto.create("veran2", "WS2021");
    d1.addVeranstaltung(v2);
    dozentenRepo.save(d1);
    repository.save(v1);
    repository.save(v2);
    Iterable<VeranstaltungDto> veranstaltungen = repository.getAllFromDozent(
        dozentenRepo.findId("dozent1"));
    veranstaltungen.forEach(v -> assertThat(d1.getVeranstaltungen().contains(v)));
  }

  @Test
  void findVeranstaltungenForStudentTest() {
    StudentDto s1 = StudentDto.create("student1");
    DozentDto dozent = DozentDto.create("dozent", "vor", "nach");
    VeranstaltungDto v1 = VeranstaltungDto.create("veran1", "WS2020");
    VeranstaltungDto v2 = VeranstaltungDto.create("veran2", "WS2021");
    studentenRepo.save(s1);
    v1.addStudent(s1);
    v2.addStudent(s1);
    dozent.addVeranstaltung(v1);
    dozent.addVeranstaltung(v2);
    dozentenRepo.save(dozent);
    repository.save(v1);
    repository.save(v2);
    Iterable<VeranstaltungDto> veranstaltungen = repository.getAllFromStudent(
        studentenRepo.findId("student1"));
    veranstaltungen.forEach(v -> assertThat(v.getStudenten().contains(s1)));
  }

  @Test
  void findVeranstaltungContainingTest() {
    VeranstaltungDto v1 = VeranstaltungDto.create("aldat", "WS2020");
    VeranstaltungDto v2 = VeranstaltungDto.create("prog", "WS2019");
    VeranstaltungDto v3 = VeranstaltungDto.create("dalten", "WS2021");
    DozentDto dozent = DozentDto.create("dozent", "vor", "nach");
    dozent.addVeranstaltung(v1);
    dozent.addVeranstaltung(v2);
    dozent.addVeranstaltung(v3);
    dozentenRepo.save(dozent);
    repository.save(v1);
    repository.save(v2);
    repository.save(v3);
    ArrayList<VeranstaltungDto> liste = new ArrayList<>();
    Iterable<VeranstaltungDto> veranstaltungen = repository.getAllContaining("al");
    veranstaltungen.forEach(v -> liste.add(v));
    assertThat(liste).contains(v1);
    assertThat(liste).doesNotContain(v2);
    assertThat(liste).contains(v3);
  }

  @Test
  void findDozentByUsernameTest() {
    DozentDto dozent1 = DozentDto.create("dozent1", "jens", "ben");
    DozentDto dozent2 = DozentDto.create("dozent2", "ann", "kathrin");
    dozentenRepo.save(dozent1);
    dozentenRepo.save(dozent2);
    Long id1 = dozentenRepo.findId("dozent1");
    Long id2 = dozentenRepo.findId("dozent2");
    assertThat(dozent1.getId()).isEqualTo(id1);
    assertThat(dozent2.getId()).isEqualTo(id2);
  }

  @Test
  void findStudentByUsernameTest() {
    StudentDto student1 = StudentDto.create("student1");
    StudentDto student2 = StudentDto.create("student2");
    studentenRepo.save(student1);
    studentenRepo.save(student2);
    Long id1 = studentenRepo.findId("student1");
    Long id2 = studentenRepo.findId("student2");
    assertThat(student1.getId()).isEqualTo(id1);
    assertThat(student2.getId()).isEqualTo(id2);
  }

  @Test
  void addAntwortenToFragenToFragebogenToVeranstaltungKnowingId() {
    FragebogenDto fragebogen = FragebogenDto.create(
        "Fragebogen zum Praktikum", PRAKTIKUM, "2020-01-01 12:00:00", "2020-05-01 12:00:00");
    FrageDto frage2 = FrageDto.createMultipleChoicefrage("Wo?");
    AuswahlDto auswahl1 = AuswahlDto.create("Ja");
    AuswahlDto auswahl2 = AuswahlDto.create("Nein");
    frage2.addChoice(auswahl1);
    frage2.addChoice(auswahl2);
    fragebogen.addFrage(frage2);
    VeranstaltungDto veranstaltung = VeranstaltungDto.create("propra5", "WS2019");
    DozentDto dozent = DozentDto.create("dozent", "vor", "nach");
    dozent.addVeranstaltung(veranstaltung);
    dozentenRepo.save(dozent);
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
    FrageDto frage2 = FrageDto.createMultipleChoicefrage("Wo?");
    fragebogen.addFrage(frage1);
    fragebogen.addFrage(frage2);
    VeranstaltungDto veranstaltung = VeranstaltungDto.create("propra4", "SoSe2018");
    DozentDto dozent = DozentDto.create("dozent", "vor", "nach");
    dozent.addVeranstaltung(veranstaltung);
    dozentenRepo.save(dozent);
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
    VeranstaltungDto veranstaltung = VeranstaltungDto.create("propra0", "WS2020");
    DozentDto dozent = DozentDto.create("dozent", "vor", "nach");
    dozent.addVeranstaltung(veranstaltung);
    dozentenRepo.save(dozent);
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
    DozentDto dozent = DozentDto.create("dozent1", "chris", "m");
    dozentenRepo.save(dozent);
    VeranstaltungDto veranstaltung = VeranstaltungDto.create("propra1", "SoSe2019");
    dozent.addVeranstaltung(veranstaltung);
    dozentenRepo.save(dozent);
    List<Set<VeranstaltungDto>> veranstaltungen = new ArrayList<>();
    Iterable<DozentDto> result = dozentenRepo.findAll();
    result.forEach(dto -> veranstaltungen.add(dto.getVeranstaltungen()));
    assertThat(veranstaltungen).isNotEmpty();
  }

  @Test
  void addStudentenToVeranstaltungTest() {
    StudentDto student1 = StudentDto.create("student1");
    studentenRepo.save(student1);
    VeranstaltungDto veranstaltung = VeranstaltungDto.create("propra2", "WS2019");
    veranstaltung.addStudent(student1);
    DozentDto dozent = DozentDto.create("dozent", "vor", "nach");
    dozent.addVeranstaltung(veranstaltung);
    dozentenRepo.save(dozent);
    repository.save(veranstaltung);
    List<Set<SBelegtVDto>> studenten = new ArrayList<>();
    Iterable<VeranstaltungDto> result = repository.findAll();
    result.forEach(dto -> studenten.add(dto.getStudenten()));
    assertThat(studenten).isNotEmpty();
  }

  @Test
  void saveVeranstaltungTest() {
    DozentDto dozent = DozentDto.create("dozent", "vor", "nach");
    VeranstaltungDto veranstaltung = VeranstaltungDto.create("Propra3", "WS2020");
    dozent.addVeranstaltung(veranstaltung);
    dozentenRepo.save(dozent);
    repository.save(veranstaltung);
    ArrayList<VeranstaltungDto> veranstaltungen = new ArrayList<>();
    Iterable<VeranstaltungDto> result = repository.findAll();
    result.forEach(dto -> veranstaltungen.add(dto));
    assertThat(veranstaltungen).isNotEmpty();
  }
}
