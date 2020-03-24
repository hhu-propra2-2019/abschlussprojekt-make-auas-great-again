package mops.filehandling;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import mops.Veranstaltung;
import mops.rollen.Dozent;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.springframework.mock.web.MockMultipartFile;

@SuppressWarnings("PMD") //PMD beschwert sich, dass ich mehrere Assertions pro Test nutze
public class CsvReaderTest {
  private transient MockMultipartFile csvfile;
  private transient MockMultipartFile textfile;
  private transient Veranstaltung mockVeranstaltung;
  private transient CsvReader csvReader;

  @BeforeEach
  public void init() {
    csvfile = new MockMultipartFile(
        "data",
        "filename.csv",
        "text/csv",
        "here,are,some,inputs".getBytes());

    textfile = new MockMultipartFile(
        "inputs",
        "filename.txt",
        "text/plain",
         "here,are,a,few,more,inputs".getBytes());

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

    assertEquals("Check message status",
        "success", csvReader.getMessageStatus()); // NOPMD
    assertThat("Is CsvReader displaying a message saying that 4 students were added?",
        csvReader.getMessage(), containsString("4")); // NOPMD
  }

  @Test
  @DisplayName("Teste null")
  public void readerComplainsWhenNoFileWasSent() {
    csvReader = new CsvReader(null, mockVeranstaltung);

    assertEquals("Check message status",
        "error", csvReader.getMessageStatus()); // NOPMD
    assertThat("Is CsvReader displaying the correct message?",
        csvReader.getMessage(), containsString("keine Datei")); // NOPMD
  }

  @Test
  @DisplayName("Dateiname null")
  public void readerComplainsWhenFileNameIsEmpty() {
    MockMultipartFile nullFile = new MockMultipartFile(
        "content",
        null,
        "text/csv",
         "this,is,unreachable,content".getBytes());

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
        "text/csv", "".getBytes()
    );

    csvReader = new CsvReader(emptyFile, mockVeranstaltung);

    thrownException.expect(NullPointerException.class);
    thrownException.expectMessage("Content null");
  }

  @Test
  @DisplayName("Veranstaltung null")
  public void readerComplainsWhenVeranstaltungNull() {
    csvReader = new CsvReader(csvfile, null);

    assertEquals("Check message status",
        "error", csvReader.getMessageStatus()); // NOPMD
    assertThat("Is CsvReader displaying the correct message?",
        csvReader.getMessage(), containsString(
            "Veranstaltung wurde nicht mitgegeben")); // NOPMD
  }
}
