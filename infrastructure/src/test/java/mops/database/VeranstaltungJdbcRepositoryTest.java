package mops.database;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import mops.database.dto.DozentDto;
import mops.database.dto.StudentDto;
import mops.database.dto.VeranstaltungDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

@DataJdbcTest
public class VeranstaltungJdbcRepositoryTest {

  @Autowired
  private VeranstaltungJdbcRepository repository;

  @Test
  void addDozentenToVeranstaltungTest() {
    DozentDto dozent = new DozentDto(37L, "chris", "m", "herr");
    VeranstaltungDto veranstaltung = new VeranstaltungDto(467L, "propra2", 3);
    veranstaltung.addDozent(dozent);

    assertThat(veranstaltung.getDozenten()).isNotEmpty();
  }

  @Test
  void addStudentenToVeranstaltungTest() {
    StudentDto student1 = new StudentDto("student1");
    VeranstaltungDto veranstaltung = new VeranstaltungDto(467L, "propra2", 3);
    veranstaltung.addStudent(student1);

    assertThat(veranstaltung.getStudenten()).isNotEmpty();
  }


  @Test
  void findVeranstaltungTest() {
    ArrayList<VeranstaltungDto> veranstaltungen = new ArrayList<>();
    Iterable<VeranstaltungDto> result = repository.findAll();
    result.forEach(dto -> veranstaltungen.add(dto));
    assertThat(veranstaltungen).isEmpty();
  }
}
