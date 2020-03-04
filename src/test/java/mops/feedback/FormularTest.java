package mops.feedback;

import java.time.LocalDate;
import mops.feedback.model.Formular;
import mops.feedback.model.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Formular Methods Test")
public class FormularTest {

  private Formular formular;

  @BeforeEach
  private void setUp(){
    LocalDate startdatum = LocalDate.of(2020, 3, 3);
    LocalDate enddatum   = LocalDate.of(2020, 3, 20);
    formular = new Formular("Programing", "Sam", null, startdatum, enddatum );

  }

  @Test
  public void getStatusTest(){
    LocalDate heute = LocalDate.of(2020, 3, 5);

    Status status = formular.getStatus(heute);

    Assertions.assertEquals(status, Status.VERFUEGBAR);
  }
}
