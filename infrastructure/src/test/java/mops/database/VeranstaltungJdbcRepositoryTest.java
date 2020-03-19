package mops.database;


import static mops.Einheit.VORLESUNG;
import static org.assertj.core.api.Assertions.assertThat;

import mops.database.dto.DozentDto;
import mops.database.dto.FrageDto;
import mops.database.dto.FragebogenDto;
import mops.database.dto.StudentDto;
import mops.database.dto.VeranstaltungDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class VeranstaltungJdbcRepositoryTest {

  @Autowired
  private VeranstaltungJdbcRepository veranstaltungsRepo;

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
  void addDataToVeranstaltungTest() {
    VeranstaltungDto veranstaltung = new VeranstaltungDto(21L, "propra", 3);
    StudentDto student = new StudentDto("student", "ann", "becker");
    DozentDto dozent = new DozentDto(26L, "jens", "be", "herr");
    FrageDto frage = new FrageDto(67L, "Warum?");
    FragebogenDto fragebogen = new FragebogenDto(49L, "Feedback Propra", 0, VORLESUNG, "2020-03-02 12:00:00", "2020-05-02 12:00:00");
    fragebogen.addFrage(frage);

    veranstaltung.addDozent(dozent);
    veranstaltung.addStudent(student);
    veranstaltung.addFragebogen(fragebogen);
    assertThat(veranstaltung.getFrageboegen()).contains(fragebogen);
  }

}
