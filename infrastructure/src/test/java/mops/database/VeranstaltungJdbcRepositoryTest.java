package mops.database;


import static mops.Einheit.PRAKTIKUM;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import mops.database.dto.DozentDto;
import mops.database.dto.FrageDto;
import mops.database.dto.FragebogenDto;
import mops.database.dto.StudentDto;
import mops.database.dto.VeranstaltungDto;
import mops.database.dto.dOrganisiertV;
import mops.database.dto.sBelegtV;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

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
  void addFragenToFragebogenToVeranstaltung() {
    FragebogenDto fragebogen = FragebogenDto.create("Fragebogen zum Praktikum", PRAKTIKUM, "2020-01-01 12:00:00", "2020-05-01 12:00:00");
    FrageDto frage1 = FrageDto.createTextfrage("Was?");
    FrageDto frage2 = FrageDto.createMultipleChoicefrage("Wo?");
    fragebogen.addFrage(frage1);
    fragebogen.addFrage(frage2);
    VeranstaltungDto veranstaltung = VeranstaltungDto.create("propra2", 3);
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
    FragebogenDto fragebogen = FragebogenDto.create("Fragebogen zum Praktikum", PRAKTIKUM, "2020-01-01 12:00:00", "2020-05-01 12:00:00");
    FrageDto frage = FrageDto.createTextfrage("Was?");
    fragebogen.addFrage(frage);
    VeranstaltungDto veranstaltung = VeranstaltungDto.create("propra2", 3);
    repository.save(veranstaltung);
    veranstaltung.addFragebogen(fragebogen);
    repository.save(veranstaltung);
    List<Set<FragebogenDto>> frageboegen = new ArrayList<>();
    Iterable<VeranstaltungDto> result = repository.findAll();
    result.forEach(dto -> frageboegen.add(dto.getFrageboegen()));
    assertThat(frageboegen).isNotEmpty();
  }

  @Test
  void addDozentenToVeranstaltungTest() {
    DozentDto dozent = DozentDto.create("chris", "m", "herr");
    dozentenRepo.save(dozent);
    VeranstaltungDto veranstaltung = VeranstaltungDto.create("propra2", 3);
    veranstaltung.addDozent(dozent);
    repository.save(veranstaltung);
    List<Set<dOrganisiertV>> dozenten = new ArrayList<>();
    Iterable<VeranstaltungDto> result = repository.findAll();
    result.forEach(dto -> dozenten.add(dto.getDozenten()));
    assertThat(dozenten).isNotEmpty();
  }

  @Test
  void addStudentenToVeranstaltungTest() {
    StudentDto student1 = StudentDto.create("student1");
    studentenRepo.save(student1);
    VeranstaltungDto veranstaltung = VeranstaltungDto.create("propra2", 3);
    veranstaltung.addStudent(student1);
    repository.save(veranstaltung);
    List<Set<sBelegtV>> studenten = new ArrayList<>();
    Iterable<VeranstaltungDto> result = repository.findAll();
    result.forEach(dto -> studenten.add(dto.getStudenten()));
    assertThat(studenten).isNotEmpty();
  }

  @Test
  void saveVeranstaltungTest() {
    VeranstaltungDto veranstaltung = VeranstaltungDto.create("Propra3", 4);
    repository.save(veranstaltung);
    ArrayList<VeranstaltungDto> veranstaltungen = new ArrayList<>();
    Iterable<VeranstaltungDto> result = repository.findAll();
    result.forEach(dto -> veranstaltungen.add(dto));
    assertThat(veranstaltungen).isNotEmpty();
  }
}
