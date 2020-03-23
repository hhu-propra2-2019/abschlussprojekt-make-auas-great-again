package mops.database;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Set;
import mops.database.dto.DozentDto;
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

  @Test
  void addDozentenToVeranstaltungTest() {
    DozentDto dozent = DozentDto.create("chris", "m", "herr");
    dozentenRepo.save(dozent);
    VeranstaltungDto veranstaltung = VeranstaltungDto.create("propra2", 3);
    veranstaltung.addDozent(dozent);
    repository.save(veranstaltung);
    ArrayList<VeranstaltungDto> veranstaltungen = new ArrayList<>();
    Iterable<VeranstaltungDto> result = repository.findAll();
    result.forEach(dto -> veranstaltungen.add(dto));
    Set<dOrganisiertV> dozenten = veranstaltung.getDozenten();
    assertThat(dozenten).isNotEmpty();
  }

  @Test
  void addStudentenToVeranstaltungTest() {
    StudentDto student1 = StudentDto.create("student1");
    studentenRepo.save(student1);
    VeranstaltungDto veranstaltung = VeranstaltungDto.create("propra2", 3);
    veranstaltung.addStudent(student1);
    repository.save(veranstaltung);
    ArrayList<VeranstaltungDto> veranstaltungen = new ArrayList<>();
    Iterable<VeranstaltungDto> result = repository.findAll();
    result.forEach(dto -> veranstaltungen.add(dto));
    Set<sBelegtV> studenten = veranstaltung.getStudenten();
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
