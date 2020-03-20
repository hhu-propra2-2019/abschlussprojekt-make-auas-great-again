package mops.fileHandling;

import org.springframework.web.multipart.MultipartFile;

public class FileHandler {
  private final String allowedFileType = "csv";

  public boolean verifyFile(MultipartFile file) {
    if (file != null) {
      String fileName = file.getOriginalFilename();
      if (fileName != null) {
        String[] parts = fileName.split("\\.");
        return (parts[parts.length - 1].equalsIgnoreCase(allowedFileType));
      }
    }
    return false;
  }
}
