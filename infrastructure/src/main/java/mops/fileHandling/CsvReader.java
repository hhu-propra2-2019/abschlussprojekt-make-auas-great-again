package mops.fileHandling;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import lombok.Getter;
import mops.Veranstaltung;
import mops.controllers.VeranstaltungsRepository;
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
  private MultipartFile file;
  @Getter
  private Veranstaltung veranstaltung;
  private VeranstaltungsRepository veranstaltungsRepo;

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

  private void readFromFile() {
    try {
      Reader reader = new InputStreamReader(file.getInputStream());
      CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT);

      int studentenCounter = 0;
      for (final CSVRecord record : parser) {
        for (int i = 0; i < record.size(); i++) {
          final String username = record.get(i);

          Student student = new Student(username);
          veranstaltung.addStudent(student);
          studentenCounter++;
        }
      }

      setMessageAndStatus(" Es wurden " + studentenCounter
          + " Studenten der Veranstaltung zugeordnet.", messageStatus = "success");
    } catch (IOException e) {
      e.printStackTrace();
      setMessageAndStatus("Es gab einen Fehler beim Lesen der Datei! "
          + "<i><b>(IOException in CsvReader.java)</b></i>", "error");
    }
  }

  private void setMessageAndStatus(String message, String messageStatus) {
    this.message = message;
    this.messageStatus = messageStatus;
  }
}
