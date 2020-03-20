package mops.fileHandling;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import lombok.Getter;
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

  public CsvReader(MultipartFile file) {
    this.file = file;

    message = "";
    messageStatus = "";

    if (isFileVerified()) {
      readFromFile();
    }

  }

  private boolean isFileVerified() {
    FileHandler fileHandler = new FileHandler();
    boolean verifictationStatus = fileHandler.verifyFile(file);
    message = fileHandler.getMessage();
    messageStatus = verifictationStatus ? "success" : "error";
    return verifictationStatus;
  }

  private void readFromFile() {
    try {
      Reader reader = new InputStreamReader(file.getInputStream());
      CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT);

      for (final CSVRecord record : parser) {
        for (int i = 0; i < record.size(); i++) {
          final String string = record.get(i);

          System.out.println(string);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
      message = "Es gab einen Fehler beim Lesen der Datei! " +
          "<i><b>(IOException in CsvReader.java)</b></i>";
      messageStatus = "error";
    }
  }
}
