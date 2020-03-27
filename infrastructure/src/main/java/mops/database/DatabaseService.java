package mops.database;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mops.Fragebogen;
import mops.database.dto.FrageDto;
import mops.database.dto.FragebogenDto;
import mops.database.repos.DozentRepository;
import mops.database.repos.FragebogenRepository;
import mops.database.repos.FragebogenTemplateRepository;
import mops.database.repos.StudentRepository;
import mops.database.repos.VeranstaltungRepository;
import mops.fragen.Frage;

@AllArgsConstructor
public class DatabaseService {
  private final DozentRepository dozentrepo;
  private final FragebogenRepository fragebogenrepo;
  private final FragebogenTemplateRepository templaterepo;
  private final StudentRepository studentrepo;
  private final VeranstaltungRepository veranstaltungsrepo;
  
  public Fragebogen getFragebogenById(Long bogennr) {
    FragebogenDto dto = fragebogenrepo.getFragebogenById(bogennr);
    return this.load(dto);
  }
  
  public Fragebogen load(FragebogenDto dto) {
    return new Fragebogen(dto.getId(),
        dto.getName(),
        List.of(dto.getFragen().stream().map(x -> load(x)).collect(Collectors.toList())),
        null, null, null, null);
  }
  
  public Frage load(FrageDto dto) {
    
  }
}
