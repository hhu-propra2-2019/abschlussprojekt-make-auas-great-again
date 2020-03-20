package mops.fileHandling;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class FileHandler {
  private String message;
  private String messageStatus;

  public boolean verifyFile(MultipartFile file) {
    if (file != null) {
      String fileName = file.getOriginalFilename();
      if (fileName != null) {
        String[] parts = fileName.split("\\.");
        if (parts[parts.length - 1].equalsIgnoreCase("csv")) {
          setMessageAndStatus("<i>Success!</i>", "success");
          return true;
        }
        setMessageAndStatus("Error! Die Datei hat ein falsches Format!",
            "error");
      } else {
        setMessageAndStatus("Oh-oh! Irgendetwas ist gewaltig schiefgelaufen! "
            + "<i>(Dateiname null)</i>", "error");
      }
    } else {
      setMessageAndStatus("Merkwürdig... irgendwie wurde keine Datei mitgegeben...",
          "error");
    }
    return false;
  }

  private void setMessageAndStatus(String message, String messageStatus) {
    this.message = message;
    this.messageStatus = messageStatus;
  }
}
