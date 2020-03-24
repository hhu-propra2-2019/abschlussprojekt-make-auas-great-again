package mops;

import java.util.List;
import mops.rollen.Dozent;
import mops.rollen.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Veranstaltung Methods Test")
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
class VeranstaltungTest {

  private transient Veranstaltung veranstaltung;

  @BeforeEach
  public void setUp() {
    veranstaltung = new Veranstaltung("ProPra2", "3", new Dozent("Jens"));
  }

  @Test
  public void addStudentTest() {
    Student student = new Student("nauth100");
    veranstaltung.addStudent(student);
    List<Student> studenten = veranstaltung.getStudenten();
    Assertions.assertTrue(studenten.contains(student));

  }

  @Test
  public void addFragebogen() {
    Fragebogen fragebogen = new Fragebogen("ProPra", "Christian Meter");
    veranstaltung.addFragebogen(fragebogen);
    List<Fragebogen> FragenList = veranstaltung.getFrageboegen();
    Assertions.assertTrue(FragenList.contains(fragebogen));

  }

  @Test
  public void getFrageboegenContainingTest() {
    Fragebogen fragebogen = new Fragebogen("Algebra", "Benjamin Klopsch");
    veranstaltung.addFragebogen(fragebogen);
    List<Fragebogen> Tested = veranstaltung.getFrageboegenContaining("Algebra");
    Assertions.assertEquals(Tested.get(0), fragebogen);

  }


}