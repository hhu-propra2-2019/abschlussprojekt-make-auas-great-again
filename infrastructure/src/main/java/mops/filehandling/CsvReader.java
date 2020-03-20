package mops.filehandling;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import lombok.Getter;
import mops.Veranstaltung;
import mops.rollen.Student;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

public class CsvReader {
  @Getter
  private String message;
  @Getter
  private String messageStatus;
  private transient MultipartFile file;
  @Getter
  private Veranstaltung veranstaltung;
  private transient int studentenCounter = 0;
  private transient Reader reader;
  private transient CSVParser parser;

  public CsvReader(MultipartFile file, Veranstaltung veranstaltung) {
    this.file = file;
    this.veranstaltung = veranstaltung;

    message = "";
    messageStatus = "";

    if (isFileVerified()) {
      readFromFile();
    }

  }

  private boolean isFileVerified() {
    FileHandler fileHandler = new FileHandler();
    boolean verifictationStatus = fileHandler.verifyFile(file);
    setMessageAndStatus(fileHandler.getMessage(), fileHandler.getMessageStatus());
    return verifictationStatus;
  }

  @SuppressWarnings("PMD")
  private void readFromFile() {
    try {
      reader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
      parser = new CSVParser(reader, CSVFormat.DEFAULT);
      for (CSVRecord record : parser) {
        for (int j = 0; j < record.size(); j++) {
          final String username = record.get(j);

          Student student = new Student(username);
          veranstaltung.addStudent(student);
          studentenCounter++;
        }
      }

      setMessageAndStatus(" Es wurden " + studentenCounter
          + " Studenten der Veranstaltung zugeordnet.", messageStatus = "success");

    } catch (IOException e) {
      setMessageAndStatus("Es gab einen Fehler beim Lesen der Datei! "
          + "<i><b>(IOException in CsvReader.java)</b></i>", "error");
    } finally {
      try {
        reader.close();
        parser.close();
      } catch (IOException e) {
        setMessageAndStatus("Es gab einen Fehler beim Versuch, den Reader/Parser "
            + "zu schließen. <i>(IOException in CsvReader.java)</i>", "error");
      }
    }
  }

  private void setMessageAndStatus(String message, String messageStatus) {
    this.message = message;
    this.messageStatus = messageStatus;
  }
}
