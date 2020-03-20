package mops.fileHandling;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

public class FileHandler {
  @Getter
  private String message;

  public boolean verifyFile(MultipartFile file) {
    if (file != null) {
      String fileName = file.getOriginalFilename();
      if (fileName != null) {
        String[] parts = fileName.split("\\.");
        if (parts[parts.length - 1].equalsIgnoreCase("csv")) {
          message = "<i>Success!</i>";
          return true;
        }
        message = "Error! Die Datei hat ein falsches Format!";
      } else {
        message = "Oh-oh! Irgendetwas ist gewaltig schiefgelaufen! <i>(Dateiname null)</i>";
      }
    } else {
      message = "Merkwürdig... irgendwie wurde keine Datei mitgegeben...";
    }
    return false;
  }
}
