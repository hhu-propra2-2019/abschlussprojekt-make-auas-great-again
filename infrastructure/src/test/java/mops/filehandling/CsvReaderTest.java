package mops.filehandling;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.nio.charset.StandardCharsets;
import mops.Veranstaltung;
import mops.rollen.Dozent;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.springframework.mock.web.MockMultipartFile;

@SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
public class CsvReaderTest {
  private transient MockMultipartFile csvfile;
  private transient MockMultipartFile textfile;
  private transient Veranstaltung mockVeranstaltung;
  private transient CsvReader csvReader;

  @BeforeEach
  public void init() {
    csvfile = new MockMultipartFile(
        "csvdata",
        "filename.csv",
        "text/csv",
        "here,are,some,inputs".getBytes(StandardCharsets.UTF_8));

    textfile = new MockMultipartFile(
        "inputs",
        "filename.txt",
        "text/plain",
        "here,are,a,few,more,inputs".getBytes(StandardCharsets.UTF_8));

    mockVeranstaltung = new Veranstaltung(
        "TestVeranstaltung",
        "SoSe2069",
        new Dozent("Brian"));
  }

  @Test
  @DisplayName("Test unterstützt nur .csv-Dateien")
  public void readerDoesNotAcceptTextFile() {
    csvReader = new CsvReader(textfile, mockVeranstaltung);

    assertEquals("Check message status",
        "error", csvReader.getMessageStatus());
    assertThat("Is the message set correctly?",
        csvReader.getMessage(), containsString("falsches Format"));
  }

  @Test
  @DisplayName("Test unterstützt .csv-Format!")
  public void readerDoesAcceptCsvFile() {
    csvReader = new CsvReader(csvfile, mockVeranstaltung);

    assertEquals("Was the import successful?",
        "success", csvReader.getMessageStatus());
    assertThat("Is CsvReader displaying a message saying that 4 students were added?",
        csvReader.getMessage(), containsString("4"));
  }

  @Test
  @DisplayName("Teste null")
  public void readerComplainsWhenNoFileWasSent() {
    csvReader = new CsvReader(null, mockVeranstaltung);

    assertEquals("Does the CsvReader return an error message?",
        "error", csvReader.getMessageStatus());
    assertThat("Is CsvReader displaying the correct message?",
        csvReader.getMessage(), containsString("keine Datei"));
  }

  @Test
  @DisplayName("Dateiname null")
  public void readerComplainsWhenFileNameIsEmpty() {
    MockMultipartFile nullFile = new MockMultipartFile(
        "content",
        null,
        "text/csv",
        "this,is,unreachable,content".getBytes(StandardCharsets.UTF_8));

    csvReader = new CsvReader(nullFile, mockVeranstaltung);

    //MockMultipartFile sets filename automatically to empty String
    assertThat("Is CsvReader displaying the correct message?",
        csvReader.getMessage(), containsString("falsches Format"));
  }

  @Rule
  public transient ExpectedException thrownException = ExpectedException.none();

  @Test
  @DisplayName("Leerer Inhalt")
  public void readerComplainsWhenInputEmpty() {
    MockMultipartFile emptyFile = new MockMultipartFile("nodata","input.csv",
        "text/csv", "".getBytes(StandardCharsets.UTF_8)
    );

    csvReader = new CsvReader(emptyFile, mockVeranstaltung);

    thrownException.expect(NullPointerException.class);
    thrownException.expectMessage("Content null");
  }

  @Test
  @DisplayName("Veranstaltung null")
  public void readerComplainsWhenVeranstaltungNull() {
    csvReader = new CsvReader(csvfile, null);

    assertEquals("Is the CsvReader returning an error when 'Veranstaltung' null?",
        "error", csvReader.getMessageStatus());
    assertThat("Is CsvReader displaying the correct message?",
        csvReader.getMessage(), containsString(
            "Veranstaltung wurde scheinbar nicht mitgegeben"));
  }
}
