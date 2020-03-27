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

@SuppressWarnings("PMD")
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
        "here,are,some,inputs".getBytes());

    textfile = new MockMultipartFile(
        "textdata",
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

    assertEquals("error", csvReader.getMessageStatus());
    assertThat(csvReader.getMessage(), containsString("falsches Format"));
  }

  @Test
  @DisplayName("Test unterstützt .csv-Format!")
  public void readerDoesAcceptCsvFile() {
    csvReader = new CsvReader(csvfile, mockVeranstaltung);

    assertEquals("success", csvReader.getMessageStatus());
    assertThat(csvReader.getMessage(), containsString("4"));
  }

  @Test
  @DisplayName("Teste null")
  public void readerComplainsWhenNoFileWasSent() {
    csvReader = new CsvReader(null, mockVeranstaltung);

    assertEquals("error", csvReader.getMessageStatus());
    assertThat(csvReader.getMessage(), containsString("keine Datei"));
  }

  @Test
  @DisplayName("Dateiname null")
  public void readerComplainsWhenFileNameIsEmpty() {
    MockMultipartFile nullFile = new MockMultipartFile(
        "nullfile",
        null,
        "text/csv",
         "this,is,unreachable,content".getBytes());

    csvReader = new CsvReader(nullFile, mockVeranstaltung);

    //MockMultipartFile sets filename automatically to empty String
    assertThat(csvReader.getMessage(), containsString("falsches Format"));
  }

  @Rule
  public ExpectedException thrownException = ExpectedException.none();

  @Test
  @DisplayName("Leerer Inhalt")
  public void readerComplainsWhenInputEmpty() {
    MockMultipartFile emptyFile = new MockMultipartFile(
        "nocontent",
        "input.csv",
        "text/csv",
        "".getBytes()
    );

    csvReader = new CsvReader(emptyFile, mockVeranstaltung);

    thrownException.expect(NullPointerException.class);
    thrownException.expectMessage("Content null");
  }

  @Test
  @DisplayName("Veranstaltung null")
  public void readerComplainsWhenVeranstaltungNull() {
    csvReader = new CsvReader(csvfile, null);

    assertEquals("error", csvReader.getMessageStatus());
    assertThat(csvReader.getMessage(), containsString(
        "Veranstaltung wurde nicht mitgegeben"));
  }
}
